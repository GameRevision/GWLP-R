#pragma once
#include "stdafx.h"
#include <string>

struct StoCPacket
{
    DWORD* PacketTemplate;
    int TemplateSize;
    DWORD HandlerFunc;
};

struct CtoSPacket
{
    DWORD* PacketTemplate;
    int TemplateSize;
};

void Update(std::string file,StoCPacket* lstemplates,int lscount,StoCPacket* gstemplates,int gscount,CtoSPacket* cltemplates,int clcount,CtoSPacket* cgtemplates,int cgcount);