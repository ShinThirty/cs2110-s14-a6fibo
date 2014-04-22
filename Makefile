SRC      = src
OUTDIR   = bin
DOC      = doc
RES      = res
README   = README.md
MAKEFILE = Makefile
DANAUS   = $(wildcard $(SRC)/danaus/*.java)
STUDENT  = $(wildcard $(SRC)/student/*.java)
BUTT     =
ARGS     =

A3 = $(DOC) \
	 $(README) \
	 $(MAKEFILE) \
	 $(RES) \
	 $(DANAUS) \
	 src/student/Butterfly.java \
	 src/student/package-info.java \
	 src/student/RandomButterfly.java 

A6 = $(DOC) \
	 $(README) \
	 $(MAKEFILE) \
	 $(RES) \
	 $(DANAUS) \
	 src/student/Butterfly.java \
	 src/student/package-info.java \
	 src/student/RandomButterfly.java 

.PHONY: build clean doc run headless testmaps a3 a6

all: build

build: $(OUTDIR)

run: build
	cd $(OUTDIR) && java danaus.Simulator $(BUTT) $(ARGS)

headless: build
	cd $(OUTDIR) && java danaus.Simulator -h $(BUTT) $(ARGS)

testmaps:
	cd $(OUTDIR);                                  \
	for map in `ls ../res/maps/*.xml`; do          \
		echo $$map;                                \
		java danaus.Simulator -h $(BUTT) -f $$map; \
		echo "";                                   \
	done                                             

a3: a3.jar
a3.jar:
	jar cf a3.jar $(A3)

a6: a6.jar
a6.jar:
	jar cf a6.jar $(A6)

$(OUTDIR): $(DANAUS) $(STUDENT)
	test -d $(OUTDIR) || mkdir $(OUTDIR)
	javac -d $(OUTDIR) $(DANAUS) $(STUDENT)

doc:
	cd doc && make

clean:
	! test -d $(OUTDIR) || rm -r $(OUTDIR)
	-rm -f *.jar
	cd doc && make clean
