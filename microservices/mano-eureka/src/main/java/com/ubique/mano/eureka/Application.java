/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubique.mano.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@EnableEurekaServer
@EnableAdminServer
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
public class Application {
	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
