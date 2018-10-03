set projectLocation="C:\Users\HP PC\Downloads\automation\DynamiteProject"

cd %projectLocation%

mvn test -DdefaultSuiteFiles=testng.xml
