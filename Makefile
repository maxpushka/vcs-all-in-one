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
	# DRY RUN: git clean -dfX -n
	rm ./test-repos -rf
	git clean -dfX
	sudo unlink /usr/local/bin/$$(echo $(bin) | rev | cut -d "/" -f 1 | rev)

test-repos: setup-git setup-hg setup-svn

setup-svn:
	mkdir -p ./test-repos/svn
	cd ./test-repos/svn && svnadmin create project1
	svn import $(shell pwd)/test-repos/svn/project1 file://$(shell pwd)/test-repos/svn/project1/trunk -m "Initial import of project1"
	cd ./test-repos/svn && svn checkout file://$(shell pwd)/test-repos/svn/project1/trunk $(shell pwd)/test-repos/svn/project1_work
	cd ./test-repos/svn && svn cleanup

setup-git:
	mkdir -p ./test-repos/git
	cd ./test-repos/git && git init --initial-branch=main

setup-hg:
	mkdir -p ./test-repos/hg
	cd ./test-repos/hg && hg init
