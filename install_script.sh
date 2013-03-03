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

# Insert the path to the folder which should include GWLPR and Reality:Shard.
# Use an absolute directory path here, not a relative one, because that would screw
# up the script later on.
INSTALLTO="$DIR/GWLPR"


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
echo "  $INSTALLTO"
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
which git
which mvn
which mysql
echo ""


echo "Creating install dir..."
mkdir "$INSTALLTO"
mkdir "$INSTALLTO/RealityShard"
if [ ! -d "$INSTALLTO" ]; then
    echo "Failed to create the directories. Check if the path is correct and you have sufficient rights."
    exit
fi
echo ""


echo "Cloning Reality:Shard..."
read -p "[Press any key to continue]" -n 1 -s
echo ""
cd "$INSTALLTO/RealityShard"
git clone https://github.com/rusty-gr/Reality-Shard-api-jv.git Api/
git clone https://github.com/rusty-gr/Reality-Shard-impl-jv.git Impl/
git clone https://github.com/rusty-gr/Reality-Shard-utils-jv.git Util/
echo ""

echo "Installing Reality:Shard..."
read -p "[Press any key to continue]" -n 1 -s
echo ""
cd "$INSTALLTO/RealityShard/Api" && mvn clean install
cd "$INSTALLTO/RealityShard/Impl" && mvn clean install
cd "$INSTALLTO/RealityShard/Util/productionhost" && mvn clean install
echo ""


echo "Cloning GWLPR..."
read -p "[Press any key to continue]" -n 1 -s
echo ""
cd "$INSTALLTO"
git clone https://github.com/GameRevision/GWLP-R.git GWLPR/
echo ""

echo "Installing GWLPR..."
read -p "[Press any key to continue]" -n 1 -s
echo ""
cd "$INSTALLTO/GWLPR"
mvn clean install
echo ""


echo "Creating database (if it does not exist)..."
read -p "[Press any key to continue]" -n 1 -s
echo ""
echo "CREATE DATABASE IF NOT EXISTS "$MYSQLDB | mysql -h $MYSQLHOST -P $MYSQLPORT -u $MYSQLUID -p $MYSQLPWD
echo ""

echo "Creating the tables..."
echo "This might overwrite or change existing tables, depending on our structure.sql script,"
echo "which can be found in: $INSTALLTO/GWLPR/database/src/main/resources"
read -p "[Press any key to continue, or STRG-C to cancel the script]" -n 1 -s
cd "$INSTALLTO/GWLPR/database/src/main/resources"
mysql -h $MYSQLHOST -P $MYSQLPORT -u $MYSQLUID -p $MYSQLPWD $MYSQLDB < structure.sql
echo ""


echo "Displaying the created directories... this might be a lot :P"
read -p "[Press any key to continue]" -n 1 -s
echo ""
cd "$INSTALLTO"
ls -R | grep ":$" | sed -e 's/:$//' -e 's/[^-][^\/]*\//--/g' -e 's/^/   /' -e 's/-/|/'
echo ""


echo "-----------------------------------------------------------------------------"
echo "Installation done."
echo ""
echo "What remains to do now, is fill the database with some basic stuff,"
echo "and start the server by executing the host application that comes"
echo "with the GWLPR project."
echo ""
echo "To do so, you can"
echo "a) Execute the SQL script that has some stored default data,"
echo "   this will probably delete any exisiting data in your database."
echo "   The script can be executed by issuing:"
echo "   mysql -h $MYSQLHOST -P $MYSQLPORT -u $MYSQLUID -p $MYSQLPWD $MYSQLDB < $INSTALLTO/GWLPR/database/src/main/resources/default_data.sql"
echo "b) Open the GWLPR project in your favourite Java-IDE and select the 'host'"
echo "   submodule. Let the IDE execute the HostApplication class."
echo "-----------------------------------------------------------------------------"
