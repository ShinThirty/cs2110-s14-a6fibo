TEXFILE = manual
TEXS    = $(wildcard *.tex) 
SHELL   = /bin/bash
TEXDIR  = $$(git rev-parse --show-toplevel)/res/tex
LATEX   = pdflatex -synctex=1 -interaction=nonstopmode -output-directory=.tex-out

all: 
	@echo "make a3"
	@echo "make a6"

a3: $(TEXFILE).tex $(TEXS)
	TEXINPUTS=$(TEXDIR):$$TEXINPUTS:. $(LATEX) "\newcommand{\ATHREE}{} \input{$(TEXFILE).tex}"
	TEXINPUTS=$(TEXDIR):$$TEXINPUTS:. $(LATEX) "\newcommand{\ATHREE}{} \input{$(TEXFILE).tex}"
	TEXINPUTS=$(TEXDIR):$$TEXINPUTS:. $(LATEX) "\newcommand{\ATHREE}{} \input{$(TEXFILE).tex}"
	mv .tex-out/*.pdf .

a6: $(TEXFILE).tex $(TEXS)
	TEXINPUTS=$(TEXDIR):$$TEXINPUTS:. $(LATEX) "\newcommand{\ASIX}{} \input{$(TEXFILE).tex}"
	TEXINPUTS=$(TEXDIR):$$TEXINPUTS:. $(LATEX) "\newcommand{\ASIX}{} \input{$(TEXFILE).tex}"
	TEXINPUTS=$(TEXDIR):$$TEXINPUTS:. $(LATEX) "\newcommand{\ASIX}{} \input{$(TEXFILE).tex}"
	mv .tex-out/*.pdf .

view: $(TEXFILE).pdf
	evince $(TEXFILE).pdf

chrome: $(TEXFILE).pdf
	google-chrome $(TEXFILE).pdf

firefox: $(TEXFILE).pdf
	firefox $(TEXFILE).pdf

purge: clean
	-rm *.pdf

clean:
	-rm *.log
	-rm *.aux
	-rm *.out
	-rm *.synctex.gz
	-rm *.toc
	-rm *.lol
	-rm *.dvi
