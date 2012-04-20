#include "stdafx.h"
#include "TemplateUpdater.h"
#include "gw1-packet-schema.hxx"
#include <fstream>
#include <iostream>

using namespace std;

PacketFieldType DecodeSimpleType(DWORD arg)
{
    PacketFieldType t;
    // decode type
    switch(arg & 0x0F)
    {
    case 3:
        t.type(PacketSimpleTypes::vec3);
        break;
    case 2:
        t.type(PacketSimpleTypes::vec2);
        break;
    case 1:
        t.type(PacketSimpleTypes::float_);
        break;

    case 0:
        t.type(PacketSimpleTypes::agentid);
        break;

    case 4:
    case 8:
        switch(arg >> 8)
        {
        case 1:
            t.type(PacketSimpleTypes::int8);
            break;
        case 2:
            t.type(PacketSimpleTypes::int16);
            break;
        case 4:
            t.type(PacketSimpleTypes::int32);
            break;
        }
        break;

    case 5:
    case 9:
        t.prefixType(PacketSimpleTypes::int16);
        t.static_(true);
        t.occurs(arg >> 8);
        t.type(PacketSimpleTypes::int8);
        break;

    case 11:
        t.prefixType(PacketSimpleTypes::int16);
        t.static_(false);
        t.occurs(arg >> 8);
        switch((arg >> 4) & 0x0F)
        {
        case 0:
            t.type(PacketSimpleTypes::int8);
            break;
        case 1:
            t.type(PacketSimpleTypes::int16);
            break;
        case 2:
            t.type(PacketSimpleTypes::int32);
            break;
        }
        break;

    case 7:
        t.occurs(arg >> 8);
        t.static_(false);
        t.prefixType(PacketSimpleTypes::int16);
        t.type(PacketSimpleTypes::utf16);
        break;

    default:
        MessageBoxA(NULL,"Unknown type","Template error",MB_OK|MB_ICONERROR);
        ExitProcess(-1);
    }
    return t;
}

PacketFieldType DecodeNested(DWORD* args,int count,int size)
{
    PacketFieldType s;
    PacketFieldType::Field_sequence member;
    
    s.static_(false);
    s.prefixType(PacketSimpleTypes::int8);
    s.type(PacketSimpleTypes::nested);
    s.occurs(size);

    // add members
    for(int i=0;i<count;++i)
        member.push_back(DecodeSimpleType(args[i]));

    s.Field(member);
    return s;
}

CommunicationDirection::Packet_sequence* GenerateStoC(StoCPacket* templates,int count)
{
    CommunicationDirection::Packet_sequence* packetsequence = new CommunicationDirection::Packet_sequence();

    for(int i=0;i<count;++i)
    {
        PacketType pack(i);
        {
            MetaInfo info;
            info.Author("GWLPR Template Updater");
            info.Description("Auto generated");
            pack.Info(info);
        }
        // loop over template args
        for(int j=1;j<templates[i].TemplateSize;++j)
        {
            // nested structs are parsed differently
            if((templates[i].PacketTemplate[j] & 0x0F) != 12)
            {
                if((templates[i].PacketTemplate[j] & 0x0F) != 6 && (templates[i].PacketTemplate[j] & 0x0F) != 10)
                    pack.Field().push_back(DecodeSimpleType(templates[i].PacketTemplate[j]));
            }
            else
            {
                pack.Field().push_back(DecodeNested(&templates[i].PacketTemplate[j+1],(templates[i].TemplateSize - j) - 1,templates[i].PacketTemplate[j] >> 8));
                break;
            }
        }
        packetsequence->push_back(pack);       
    }
    return packetsequence;
}

CommunicationDirection::Packet_sequence* GenerateCtoS(CtoSPacket* templates,int count)
{
    CommunicationDirection::Packet_sequence* packetsequence = new CommunicationDirection::Packet_sequence();

    for(int i=0;i<count;++i)
    {
        PacketType pack(i);
        {
            MetaInfo info;
            info.Author("GWLPR Template Updater");
            info.Description("Auto generated");
            pack.Info(info);
        }
        // loop over template args
        for(int j=1;j<templates[i].TemplateSize;++j)
        {
            // nested structs are parsed differently
            if((templates[i].PacketTemplate[j] & 0x0F) != 12)
            {
                if((templates[i].PacketTemplate[j] & 0x0F) != 6 && (templates[i].PacketTemplate[j] & 0x0F) != 10)
                    pack.Field().push_back(DecodeSimpleType(templates[i].PacketTemplate[j]));
            }
            else
            {
                pack.Field().push_back(DecodeNested(&templates[i].PacketTemplate[j+1],(templates[i].TemplateSize - j) - 1,templates[i].PacketTemplate[j] >> 8));
                break;
            }
        }
        packetsequence->push_back(pack);  
    }
    return packetsequence;
}


auto_ptr<Packets> GenerateXML(StoCPacket* lstemplates,int lscount,StoCPacket* gstemplates,int gscount,CtoSPacket* cltemplates,int clcount,CtoSPacket* cgtemplates,int cgcount)
{
    auto_ptr<Packets> packets(new Packets());
    try
    {
        Packets::Direction_sequence* dir = new Packets::Direction_sequence();
        // generate DOM for all directions
        {
            CommunicationDirection direction("LoginServer to Client","LStoC");
            direction.Packet(*GenerateStoC(lstemplates,lscount));
            dir->push_back(direction);
        }
        {
            CommunicationDirection direction("GameServer to Client","GStoC");
            direction.Packet(*GenerateStoC(gstemplates,gscount));
            dir->push_back(direction);
        }
        {
            CommunicationDirection direction("Client to LoginServer","CtoLS");
            direction.Packet(*GenerateCtoS(cltemplates,clcount));
            dir->push_back(direction);
        }
        {
            CommunicationDirection direction("Client to GameServer","CtoGS");
            direction.Packet(*GenerateCtoS(cgtemplates,cgcount));
            dir->push_back(direction);
        }

        packets->Direction(*dir);
    }
    catch ( const xml_schema::exception& e)
    {
    	MessageBoxA(NULL,e.what(),"XML Error2",MB_OK|MB_ICONERROR);
        ExitProcess(-1);
    }

    return packets;
}

bool Match(PacketFieldType::Field_sequence A, PacketFieldType::Field_sequence B)
{
    if(A.size() != B.size())
        return false;
    for(int i=0;i<A.size();++i)
    {
            if(A[i].type() != B[i].type())
                return false;
    }
    return true;
}

void Update(string file,StoCPacket* lstemplates,int lscount,StoCPacket* gstemplates,int gscount,CtoSPacket* cltemplates,int clcount,CtoSPacket* cgtemplates,int cgcount)
{
    try
    {
        auto_ptr<Packets> old_packs(Packets_(file,xml_schema::flags::dont_validate));
        auto_ptr<Packets> new_packs(GenerateXML(lstemplates,lscount,gstemplates,gscount,cltemplates,clcount,cgtemplates,cgcount));

        // loop over directions
        for(auto new_direction = new_packs->Direction().begin();new_direction != new_packs->Direction().end();++new_direction)
        {
            bool direction_found = false;
            // find matching direction in old xml
            for(auto old_direction = old_packs->Direction().begin();old_direction != old_packs->Direction().end();++old_direction)
            {
                direction_found = true;
                // check for match
                if(new_direction->abbr() == old_direction->abbr())
                {
                    // loop over all packets
                    for(auto new_packet = new_direction->Packet().begin();new_packet != new_direction->Packet().end();++new_packet)
                    {
                        bool header_found = false;
                        // find matching packet in old xml
                        for(auto old_packet = old_direction->Packet().begin();old_packet != old_direction->Packet().end();++old_packet)
                        {
                            // check for match
                            if(new_packet->header() == old_packet->header())
                            {
                                header_found = true;
                                // update headers if necessary
                                if(!Match(new_packet->Field(),new_packet->Field()))
                                {
                                    for(auto update_packet = old_direction->Packet().begin();update_packet != old_direction->Packet().end();++update_packet)
                                    {
                                        if(update_packet->header() >= new_packet->header())
                                            ++update_packet->header();
                                    }
                                    // insert new packet
                                    old_direction->Packet().insert(old_packet,*new_packet);
                                }
                                // we found our match lets break out
                                break;
                            }
                        }
                        // a new packet at the end
                        if(!header_found)
                            old_direction->Packet().push_back(*new_packet);
                    }
                    // we found our match lets break out
                    break;
                }
            }
            // Houston we have a problem
            if(!direction_found)
                old_packs->Direction().push_back(*new_direction);
        }
        ofstream output(file);

        xml_schema::namespace_infomap map;
        map[""].name = "";
        map[""].schema = "gw1-packet-schema.xsd";

        Packets_(output,*old_packs,map);
    }
    catch ( const xml_schema::exception& e)
    {
        try
        {
            // no xml found. generate from scratch
            auto_ptr<Packets> new_packs(GenerateXML(lstemplates,lscount,gstemplates,gscount,cltemplates,clcount,cgtemplates,cgcount));

            xml_schema::namespace_infomap map;
            map[""].name = "";
            map[""].schema = "gw1-packet-schema.xsd";


            ofstream output(file);
            Packets_(output,*new_packs,map);
        }
        catch ( const xml_schema::exception& e)
        {
            MessageBoxA(NULL,e.what(),"XML Error1",MB_OK|MB_ICONERROR);
            ExitProcess(-1);
        }
    }
}

