#!/usr/bin/env bash

echo "Listing files"
ls bin/* -R
echo "Starting class server"
java src/classserver/ClassFileServer.java 2000
