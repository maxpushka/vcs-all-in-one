jar = app/build/libs/app-uber.jar
bin = app/build/libs/vcs

build: $(jar) $(bin)

$(jar):
	./gradlew uberJar

$(bin):
	echo "#!/bin/sh" >> $(bin)
	echo 'java -jar $(shell pwd)/$(jar) "$$@"' >> $(bin)
	chmod +x $(bin)

install: build
	sudo ln -s $(shell pwd)/$(bin) /usr/local/bin

clean:
	./gradlew clean

test-repos: setup-git setup-hg setup-svn

setup-svn:
	mkdir -p ./test-repos/svn
	cd ./test-repos/svn && svn admin create project1
	svn import $(pwd)/test-repos/svn/project1 file://$(pwd)/test-repos/svn/project1/trunk -m "Initial import of project1"

setup-git:
	mkdir -p ./test-repos/git
	cd ./test-repos/git && git init --initial-branch=main

setup-hg:
	mkdir -p ./test-repos/hg
	cd ./test-repos/hg && hg init

