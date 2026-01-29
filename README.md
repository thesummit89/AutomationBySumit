<forkedProcessTimeoutInSeconds>300</forkedProcessTimeoutInSeconds>
    <redirectTestOutputToFile>false</redirectTestOutputToFile>
    <printSummary>true</printSummary>

    

    MAVEN_OPTS: "-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -Djansi.force=true"
    
   stdbuf -oL -eL mvn clean test

    - echo "Tests finished at $(date)"
