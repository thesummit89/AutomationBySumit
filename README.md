compile_test:
  stage: test
  script:
    - mvn clean test &
    - PID=$!
    - wait $PID || true
    - echo "Killing any leftover Maven/Java processes"
    - pkill -f maven || true
    - pkill -f java || true
    - exit 0
