# Exchange Rate Logger

This exports the most recent exchange rates, using USD as the base rate, from the [Open Exchange Rates API](https://openexchangerates.org/) to MySQL.

Create the exchange rate table in MySQL:

    use oer;
    create table exchange_rate (
        currency varchar(20),
        rate double,
        primary key(currency)
    );

It'll also be necessary to update the API key and MySQL credentials in `src/main/resources/application.properties`

Run the application:

    nohup mvn spring-boot:run >/dev/null 2>&1 &

This will update the `exchange_rate` table. These updates will be captured by [Maxwell's Daemon](http://maxwells-daemon.io/) and published to Kafka. To start Maxwell:

    nohup /opt/maxwell/bin/maxwell --user='maxwell' --password='********' --host='127.0.0.1' --include_dbs=oer --producer=kafka --kafka.bootstrap.servers=hadoop02:6667 >/dev/null 2>&1 &
