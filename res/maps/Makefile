all: README.html

README.html: README.md
	gitdown $< > $@

clean:
	-rm -f README.html
