# AnvaAnalyzer



_Building_
1. `mvn install -T 1C`

_Running_
1. `mvn package -T 1C`
2. `java -jar target/anva-analyzer-*.jar`

_Releasing_
1. build and test the main branch as per 'Building'
2. git tag -a X.Y.Z 
3. git push --follow-tags
g
(See: https://jgitver.github.io/ for how the above works)

For the endpoints, see the [Api spec](/src/main/resources/api.yml).

_Requirements_


