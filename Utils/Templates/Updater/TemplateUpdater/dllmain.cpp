#include "stdafx.h"
#include "TemplateUpdater.h"

BYTE* LoadFuncAddrStart = NULL;
BYTE* LoadFuncAddrEnd = NULL;
BYTE* LoadCtoSTemplateStart = NULL;
BYTE* LoadCtoSTemplateEnd = NULL;

StoCPacket* GStoC = NULL;
int GStoCCount = 0;
StoCPacket* LStoC = NULL;
int LStoCCount = 0;
CtoSPacket* CtoGS = NULL;
int CtoGSCount = 0;
CtoSPacket* CtoLS = NULL;
int CtoLSCount = 0;


void __stdcall SaveStoCTable(StoCPacket* templates,int count)
{
    if(count > 100)
    {
        GStoC = templates;
        GStoCCount = count;
    }
    else
    {
        LStoC = templates;
        LStoCCount = count;
    }
}

void __declspec(naked) LoadFuncAddrHook()
{
    __asm
    {
        PUSHAD
        PUSH DWORD PTR DS:[ECX + 0x34]
        PUSH DWORD PTR DS:[ECX + 0x2C]
        CALL SaveStoCTable
        POPAD
        JMP LoadFuncAddrEnd
    }
}

void __stdcall SaveCtoSTable(CtoSPacket* templates,int count)
{
    if(count > 100)
    {
        CtoGS = templates;
        CtoGSCount = count;
    }
    else
    {
        CtoLS = templates;
        CtoLSCount = count;
    }
}

void __declspec(naked) LoadCtoSTemplateHook()
{
    __asm
    {
        PUSHAD
        PUSH DWORD PTR DS:[ECX + 0x24]
        PUSH DWORD PTR DS:[ECX + 0x1C]
        CALL SaveCtoSTable
        POPAD
        JMP LoadCtoSTemplateEnd
    }
}

void *DetourFunc(BYTE *src, const BYTE *dst, const int len)
{
    BYTE *jmp = (BYTE*)malloc(len+5);
    DWORD dwBack;

    VirtualProtect(src, len, PAGE_READWRITE, &dwBack);

    memcpy(jmp, src, len);	
    jmp += len;

    jmp[0] = 0xE9;
    *(DWORD*)(jmp+1) = (DWORD)(src+len - jmp) - 5;

    src[0] = 0xE9;
    *(DWORD*)(src+1) = (DWORD)(dst - src) - 5;

    for( int i=5; i < len; i++ )
        src[i] = 0x90;

    VirtualProtect(src, len, dwBack, &dwBack);

    return (jmp-len);
}

void RetourFunc(BYTE *src, BYTE *restore, const int len)
{
    DWORD dwBack;

    VirtualProtect(src, len, PAGE_READWRITE, &dwBack);
    memcpy(src, restore, len);

    restore[0] = 0xE9;
    *(DWORD*)(restore+1) = (DWORD)(src - restore) - 5;

    VirtualProtect(src, len, dwBack, &dwBack);
}

BYTE* GetLoadFuncAddr()
{
    BYTE* start = (BYTE*)0x00401000;
    BYTE* end = (BYTE*)0x00900000;

    BYTE LoadFuncCode[] = { 0x8B, 0x4F, 0x2C, 0x8D, 0x04, 0x76 };
    while(start != end)
    {
        if(!memcmp(start,LoadFuncCode,sizeof(LoadFuncCode)))
            return start - 0x10;

        start++;
    }

    return NULL;
}

BYTE* GetLoadCtoSTemplateAddr()
{
    BYTE* start = (BYTE*)0x00401000;
    BYTE* end = (BYTE*)0x00900000;

    BYTE LoadCtoSTemplateCode[] = { 0x8B, 0x47, 0x1C, 0x8D, 0x34, 0xF0, 0x8B, 0x46, 0x04 };
    while(start != end)
    {
        if(!memcmp(start,LoadCtoSTemplateCode,sizeof(LoadCtoSTemplateCode)))
            return start - 0x38;

        start++;
    }

    return NULL;
}

DWORD WINAPI InputThread(LPVOID lpParam)
{
    while(true)
    {
        Sleep(0);
        if(GetAsyncKeyState(VK_INSERT) & 1)
        {
            Update("PacketTemplates.xml",LStoC,LStoCCount,GStoC,GStoCCount,CtoLS,CtoLSCount,CtoGS,CtoGSCount);
        }
        if(GetAsyncKeyState(VK_DELETE) & 1)
        {
            CreateThread(0,0,(LPTHREAD_START_ROUTINE)FreeLibrary,lpParam,0,0);
            return 0;
        }
    }
}

BOOL APIENTRY DllMain( HMODULE hModule,DWORD ul_reason_for_call,LPVOID lpReserved)
{
	switch (ul_reason_for_call)
	{
	case DLL_PROCESS_ATTACH:
        DisableThreadLibraryCalls(hModule);

        LoadFuncAddrStart = GetLoadFuncAddr();
        if(!LoadFuncAddrStart)
        {
            MessageBoxA(0,"Unable to retrieve LoadFuncAddr addr","Error",MB_OK|MB_ICONERROR);
            return false;
        }
        LoadFuncAddrEnd = (BYTE*)DetourFunc(LoadFuncAddrStart,(BYTE*)LoadFuncAddrHook,6);

        LoadCtoSTemplateStart = GetLoadCtoSTemplateAddr();
        if(!LoadCtoSTemplateStart)
        {
            MessageBoxA(0,"Unable to retrieve LoadCtoSTemplate addr","Error",MB_OK|MB_ICONERROR);
            return false;
        }
        LoadCtoSTemplateEnd = (BYTE*)DetourFunc(LoadCtoSTemplateStart,(BYTE*)LoadCtoSTemplateHook,6);

        CreateThread(NULL,0,InputThread,hModule,0,0);
        break;
	case DLL_PROCESS_DETACH:
        RetourFunc(LoadFuncAddrStart,LoadFuncAddrEnd,6);
        RetourFunc(LoadCtoSTemplateStart,LoadCtoSTemplateEnd,6);
		break;
	}
	return TRUE;
}

