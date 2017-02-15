
copy C:\Users\newelly\git\PersonnelTracker\target\PersonnelTracker.war . /y
jar uvf PersonnelTracker.war ./WEB-INF/classes/*
move PersonnelTracker.war c:\backup\.