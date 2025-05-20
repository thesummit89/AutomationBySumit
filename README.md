stages:
  - test
  - report

variables:
  MAVEN_CLI_OPTS: "-B -Dmaven.repo.local=.m2/repository"
  TAGS: "@smoke" # Default tag; can be overridden manually
  PARALLEL_THREADS: "4" # Adjust thread count as needed

before_script:
  - echo "Running tests with tags: $TAGS"
  - echo "Parallel threads: $PARALLEL_THREADS"
  - mvn $MAVEN_CLI_OPTS clean

test-job:
  stage: test
  script:
    - mvn $MAVEN_CLI_OPTS test \
        -Dcucumber.filter.tags="$TAGS" \
        -Dthreads="$PARALLEL_THREADS" \
        -Dsurefire.parallel=classes \
        -Dsurefire.threadCount="$PARALLEL_THREADS" \
        -Dallure.results.directory=target/allure-results
  artifacts:
    when: always
    paths:
      - target/allure-results
    expire_in: 1 week

generate-allure-report:
  stage: report
  image: registry.gitlab.com/<your-org>/<your-project>/allure-docker-service:latest # or use a custom Docker image with allure installed
  script:
    - allure generate target/allure-results --clean -o target/allure-report
    - echo "Allure report generated."
  artifacts:
    when: always
    paths:
      - target/allure-report
    expire_in: 1 week
  needs: ["test-job"]
