#!/bin/sh
COMMITS=$(git log --oneline --reverse | tail -n+2 | cut -d " " -f 1)
CODE=0

git reset --hard
 
for COMMIT in $COMMITS
do
    git checkout $COMMIT

    # run-tests
    mvn clean test

    if [ $? -eq 0 ]
    then
        echo $COMMIT - passed
    else
        echo $COMMIT - failed
        exit
    fi

    git reset --hard
done
 
git checkout master