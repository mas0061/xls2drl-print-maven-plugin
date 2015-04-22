# xls2drl-print-maven-plugin

## Overview

Maven plugin for changing the decision table of Excel into DRL and displaying it.

## Usage
```xml
<build>
	<plugins>
		<plugin>
			<groupId>net.mas0061.brms.drools.plugins</groupId>
			<artifactId>xls2drl-print-maven-plugin</artifactId>
			<version>1.0.1</version>
			<executions>
				<execution>
					<phase>process-resources</phase>
					<goals>
						<goal>print</goal>
					</goals>
				</execution>
			</executions>
		</plugin>
	</plugins>
</build>
```

## Configulation
* `resourceDirectory` Target path of decision tables

## License

* [MIT License](http://opensource.org/licenses/MIT)