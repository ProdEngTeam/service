url="https://stunning-umbrella-wjx7wv9j66x3g9g-8080.app.github.dev/loans/get-loans"
request_count=10000
sleep_time=0.1

for (( i=1; i<=request_count; i++ ))
do
  echo "Sending request $i"
  curl $url
  sleep $sleep_time
done

echo "Completed sending requests."