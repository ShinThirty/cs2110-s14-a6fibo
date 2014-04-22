\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{pset}
\LoadClass[12pt]{article}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Package Imports %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\RequirePackage{2110}
\RequirePackage{java}
\RequirePackage{hyperref}
\RequirePackage{fourier}
\RequirePackage{DejaVuSansMono}
\RequirePackage[letterpaper,margin=1in]{geometry}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Page Layout %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\setlength\parindent{1em}
\addtolength\parskip{0.1cm}
\setlength\fboxrule{.5mm}\setlength{\fboxsep}{1.2mm}
\newlength\boxlength\setlength\boxlength\textwidth
\addtolength\boxlength{-4mm}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Assignment Header %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\newcommand\PSNUM{}
\newcommand\PSNAME{}
\newcommand\psname[1]{\renewcommand\PSNAME{#1}}
\newcommand\psnum[1]{\renewcommand\PSNUM{#1}}

\newcommand\instructions{\section*{Instructions}}

\newcommand\DATE{}
\renewcommand\date[1]{\renewcommand{\DATE}{#1}}
\newcommand\psdue[1]{\date{Due at 11:59 PM, #1}}

\newcommand\VERSIONNUMBER{}
\newcommand\versionnumber[1]{\renewcommand{\VERSIONNUMBER}{#1}}

\newcommand\DATEMODIFIED{}
\newcommand\datemodified[1]{\renewcommand{\DATEMODIFIED}{#1}}
\datemodified{\today}

\renewcommand\maketitle{%
	\vspace*{-5em}
	\begin{center}\framebox[\linewidth]{%
	\parbox{\boxlength}{\bf%
		Object-Oriented Programming and Data Structures\hfill
        Problem Set \PSNUM \PSNAME\\ 
		CS 2110, Spring 2014\hfill \DATE \\
        Version: \VERSIONNUMBER \hfill Last Modified: \DATEMODIFIED
	}}
	\end{center}
	\vspace{1em}
}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Standard Instructions %%%%%%%%%%%%%%%%%%%%%%%%%%%

\newcommand{\standardinstructions}{%
  \section*{Preamble}
  Please read the whole handout before starting. At the end of this handout, we
  will tell you what and how to submit.  We will ask you for the time spent on
  this assignment, so \textit{please keep track of the time you spent on it}.  
  
  \subsection*{Collaboration Policy and Academic Integrity}
  You may do this assignment with one other person. Both members of the group
  should get on the CMS and do what is required to form a group well before the
  assignment due date. Both must do something to form the group: one proposes,
  the other accepts.

  People in a group must \textit{work together}. It is against the rules for
  one person to do some programming on this assignment without the other person
  sitting nearby and helping. Take turns ``driving'' -- using the keyboard and
  mouse.  With the exception of your CMS-registered group partner, you may not
  look at anyone else's code, in any form, or show your code to anyone else
  (except the course staff), in any form. You may not show or give your code to
  another student in the class.
  
  \subsection*{Code Style}
  Finally, please pay attention to style. Refer to the
  \link{http://www.cs.cornell.edu/Courses/cs2110/2013fa/style_guidelines.html}{CS
  2110 style guide} and lecture notes. Ugly code that is functionally correct
  may still lose points. Take the extra time to think out the problems and find
  the most elegant solutions before coding them up. Good programming style is
  important for all assignments throughout the semester.
}


% Problems and solutions %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\newcommand*{\spelled}[1]{%
  \expandafter\@spelled\csname c@#1\endcsname
}
\newcommand*{\@spelled}[1]{%
  \ifcase#1\or{One}\or{Two}\or{Three}\or{Four}\or{Five}\or{Six}\or{Seven}\or{Eight}\or{Nine}\or{Ten}\else\@ctrerr\fi
}

\newcounter{problem}
\newcounter{exercise}
\newcounter{pspart}

\renewcommand{\part}[1]{
	\addtocounter{pspart}{1}
	\section*{Part \spelled{pspart}: #1}
}

\newcommand\bracket[1]{\ifx#1\empty\else[#1]\fi}

\newcommand\problem[2]{
	\refstepcounter{problem}
	\setcounter{exercise}{0}
	\section*{Problem \theproblem: #1 {\normalsize(#2 pts)}}
}

\newcommand\exercise[1]{
	\refstepcounter{exercise}
	\section*{Exercise \theexercise: #1}
}

\newcommand\examples{\subsubsection*{Examples}}
%
% vim: syntax=tex
%
