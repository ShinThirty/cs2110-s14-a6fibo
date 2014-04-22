SOURCE_PATH = ../src
DEST = javadoc
PACKAGES = danaus student

all: doc

doc:
	javadoc -sourcepath $(SOURCE_PATH) -d $(DEST) $(PACKAGES)
	cd manual && make
	cd A3 && make

private:
	javadoc -private -sourcepath $(SOURCE_PATH) -d $(DEST) $(PACKAGES)

clean:
	! test -d $(DEST) || rm -r $(DEST)
