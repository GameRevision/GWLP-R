#!/bin/sh

##
# For copyright information see the LICENSE document.
# Created by rusty-gr.
##

# This can be used to install Reality:Shard and the GWLPR server.
# ...ON A LINUX / UNIX SYSTEM!
#
# To execute this and the server, you will need to have the following tools installed:
# - GIT
# - Java 7 (JRE/JDK 1.7)
# - Maven 3
# - MySQL and a running server instance of it


DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"


# MySQL host, port, user id, password and database.
# When using the default settings, the script will access
# a fresh/clean and local installation of a mysql server
# and create a gwlpr database and its tables.
# If the password is empty, the script will ask you to enter it
# later on. (If you dont have one, simply press enter)
MYSQLHOST="127.0.0.1"
MYSQLPORT="3306"
MYSQLUID="root"
MYSQLPWD=""
MYSQLDB="testgwlpr"


clear
echo "-----------------------------------------------------------------------------"
echo "< GWLPR Install >"
echo "-----------------------------------------------------------------------------"
echo "The script was started with the following settings:"
echo ""
echo "Installation-path:"
echo "  $DIR"
echo ""
echo "MySQL-settings:"
echo "  server:   $MYSQLHOST"
echo "  port:     $MYSQLPORT"
echo "  user:     $MYSQLUID"
echo "  pwd:      $MYSQLPWD"
echo "  database: $MYSQLDB"
echo ""
echo "Hints:"
echo "If your running maven for the first time with any projects, it may take"
echo "a while to download the additional dependencies of this project."
echo "When the database is installed the software might require a password."
echo "In that case, just press [ENTER] if you dont have one."
echo "-----------------------------------------------------------------------------"
echo ""
read -p "[Press any key to continue]" -n 1 -s
echo ""


echo "Checking installed tools..."
command -v git >/dev/null 2>&1 || { echo >&2 "Failed to find git. Did you install it correctly?"; exit 1; }
command -v mvn >/dev/null 2>&1 || { echo >&2 "Failed to find maven. Did you install it correctly?"; exit 1; }
command -v mysql >/dev/null 2>&1 || { echo >&2 "Failed to find mysql. Did you install it correctly?"; exit 1; }
echo ""


echo "Creating Reality:Shard install dir..."
mkdir "$DIR/RealityShard"
if [ ! -d "$DIR/RealityShard" ]; then
    echo "Failed to create the directories. Check if the path is correct and you have sufficient rights."
    exit 1
fi
echo ""


echo "Cloning Reality:Shard..."
read -p "[Press any key to continue]" -n 1 -s
echo ""
git clone https://github.com/RealityShard/RealityShard.git "$DIR/RealityShard"
echo ""


echo "Installing Reality:Shard..."
read -p "[Press any key to continue]" -n 1 -s
echo ""
cd "$DIR/RealityShard" && chmod +x install.sh && ./install.sh
echo ""


echo "Installing GWLPR..."
read -p "[Press any key to continue]" -n 1 -s
echo ""
cd "$DIR"
mvn clean install
echo ""


echo "Creating database (if it does not exist)..."
read -p "[Press any key to continue]" -n 1 -s
echo ""
echo "CREATE DATABASE IF NOT EXISTS $MYSQLDB" | mysql -h$MYSQLHOST -P$MYSQLPORT -u$MYSQLUID -p$MYSQLPWD
echo ""


echo "Creating the tables..."
echo "This might overwrite or change existing tables, depending on our structure.sql script,"
echo "which can be found in: $DIR/database/src/main/resources"
read -p "[Press any key to continue, or STRG-C to cancel the script]" -n 1 -s
cd "$DIR/database/src/main/resources"
mysql -h$MYSQLHOST -P$MYSQLPORT -u$MYSQLUID -p$MYSQLPWD $MYSQLDB < structure.sql
echo ""


echo "-----------------------------------------------------------------------------"
echo "GWLPR Installation done."
echo ""
echo "What remains to be done now, is filling the database with some basic stuff,"
echo "and starting the server by executing the host application that comes"
echo "with the GWLPR project."
echo ""
echo "To do so, you can"
echo "a) Execute the SQL script that has some stored default data,"
echo "   this will probably delete any exisiting data in your gwlpr database."
echo "   The script can be executed by issuing:"
echo ""
echo "mysql -h$MYSQLHOST -P$MYSQLPORT -u$MYSQLUID -p$MYSQLPWD $MYSQLDB < $DIR/database/src/main/resources/default_data.sql"
echo ""
echo "b) Open the GWLPR project in your favourite Java-IDE and select the 'host'"
echo "   submodule. Let the IDE execute the HostApplication class."
echo ""
echo "If there were any errors during installation, please report them on"
echo "https://github.com/GameRevision/GWLP-R"
echo "-----------------------------------------------------------------------------"
