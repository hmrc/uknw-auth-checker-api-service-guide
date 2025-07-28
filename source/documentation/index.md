---
title: Notification of Presentation Waiver Checker Service Guide
weight: 1
---

# Notification of Presentation Waiver Checker Service Guide

Version 1.0 issued 24 June 2024
***

This guide explains how Community System Providers (CSPs) and agents can use the Notification of Presentation (NOP) waiver checker API at inventory linked ports. It details the process of passing Economic Operators Registration and Identification (EORI) numbers to check NOP waiver validity.

It is intended to help software developers, and anyone involved in integrating software that uses the API.

The NOP waiver checker API is only for use by CSPs and agents at inventory linked ports for the purposes of validating the NOP waiver, and for software developers supporting those parties.

Having an EORI number is essential for anyone involved in international trade, as it allows customs authorities to monitor and track shipments effectively.

## Overview

This API enables agents and CSPs to check if traders have a valid NOP waiver authorisation, using their EORI number. This waiver is required for the transit of Not At Risk (NAR) goods moving through from Great Britain to Northern Ireland.

The API follows REST principles and has a single POST method endpoint that returns the data in JSON format. It uses standard HTTP error response codes. Use this API to request the NOP waiver authorisation status of between 1 and 3000 EORI numbers passed as an array.

The API endpoint relates only to Great Britain and Northern Ireland.

### What is an EORI number?

Economic Operators Registration and Identification numbers are unique identification numbers used by customs authorities across the European Union (EU) to track imports and exports. Any business or individual shipping goods internationally will have an EORI number. This API uses EORI numbers to check the authorisation of NOP waivers. This system replaced the Trader’s Unique Reference Number (TURN) system. For those in the UK, HMRC issues these numbers.

A typical EORI number for UK VAT-registered businesses is as follows:

```bash
GB205672212000 # EORI number originating in GB (Wales, England or Scotland)
XI347643313000 # EORI number originating in NI
```

Below is a breakdown of this EORI number format:

- GB or XI: Indicates that the business is based either in GB or NI (XI).
- 205672212 or 347643313: Represents the business’s VAT registration number.
- 000: These 3 zeros are always added to the end of an EORI number.

**Note:** The total number of characters permitted for the EORI number (including the prefix GB or XI) is between 12 and 15.

In summary, having an EORI number is essential for anyone involved in international trade, as it allows customs authorities to monitor and track shipments effectively.

## API workflow

<img src="images/NOP-sequence-diagram.svg" alt="NOP waiver checker API sequence diagram">

This sequence outlines the process for checking authorisations using the NOP waiver checker API.

1. **Request access token:** The compatible software asks the API platform for an access token by sending a GET request which is secure and private.
2. **Receive access token:** The API platform provides an access token, which is valid for 4 hours. It will be used to verify future requests.
3. **Submit authorisation request:** Using the access token, the compatible software sends a POST request to the `/customs/uk-notice-of-presentation-waiver/authorisations` endpoint.
4. **Receive authorisation result:** The NOP waiver checker API processes the request and replies with an HTTP 200 status code and a JSON response containing the authorisation result.

## Developer setup

In the context of this API, the term ‘developer’ refers to software developers who work directly for agents and CSPs, or are contracted to agents and CSPs through software providers.

### Getting started

You must follow these steps before you can use your software to access this API in the sandbox and production environments:

1. [Sign up for a developer account](https://developer.service.hmrc.gov.uk/developer/registration).
2. [Create a sandbox application](https://developer.service.hmrc.gov.uk/api-documentation/docs/using-the-hub).
3. This API and the documentation are now publicly accessible and are available for self-subscription and testing in the sandbox environment.
4. [Learn about application-restricted endpoints](https://developer.service.hmrc.gov.uk/api-documentation/docs/authorisation/application-restricted-endpoints). This API uses the [open standard OAuth2.0](https://oauth.net/2/) with the [Client Credentials Grant](https://oauth.net/2/grant-types/client-credentials/) to generate an access token.

To develop using the NOP waiver checker API, you must:

- be familiar with HTTP, RESTful services, JSON and OAuth2
- be registered as a developer on the HMRC Developer Hub

Each application that you register will be assigned an HMRC ‘ApplicationId’.

You can [manage all the applications you have registered on Developer Hub](/developer/applications). There, you can administer things like API subscriptions and [application credentials](/api-documentation/docs/authorisation/credentials).

### Making API requests

Before sending any requests to the NOP waiver checker API, make sure that you are addressing these points in your software:

- Correct URL for the environment and API version number.
- Correct header contents and payload information.

The base URLs of the sandbox and production environments are:

```text
Sandbox	– https://test-api.service.hmrc.gov.uk/customs/uk-notice-of-presentation-waiver/

Production – https://api.service.hmrc.gov.uk/customs/uk-notice-of-presentation-waiver/
```

## End-to-end journeys

- [Developer journey overview](#developer-journey)
- [User journey overview](#user-journey)

### Developer journey

Follow this end-to-end journey to set up your developer environment, request NOP waiver authorisation using EORI numbers, and move your application to the production environment.

1. [Complete the developer setup steps](/guides/uknw-auth-checker-api-service-guide/#developer-setup)
2. [Read the making API requests guidance](/guides/uknw-auth-checker-api-service-guide/#making-api-requests) guidance
3. [Send a POST request to generate an access token](/api-documentation/docs/authorisation/application-restricted-endpoints#getting-access-token):

	```bash
	curl -X POST -H "content-type: application/x-www-form-urlencoded" --data \
	"client_secret=[YOUR-CLIENT-SECRET] \
	&client_id=[YOUR-CLIENT-ID] \
	&grant_type=client_credentials \
	https://test-api.service.hmrc.gov.uk/oauth/token
	```

4. Receive the access token from the response and store it.
5. [Get your application ready for testing in the sandbox environment](https://developer.service.hmrc.gov.uk/api-documentation/docs/testing). This API does not feature user-restricted endpoints; please disregard any information on this.
6. Send a POST request to the `/customs/uk-notice-of-presentation-waiver/authorisations` endpoint. Include an array that contains between 1 and 3000 EORI numbers.
Here is an example of a request for a single EORI number:

	```bash
	curl -XPOST \
		-H 'Accept: application/vnd.hmrc.1.0+json' \
		-H 'Authorization: Bearer YOUR_BEARER_TOKEN' \
		-H "Content-type: application/json" \
		-d '{
			"eoris": [
			"GB123123123000"
			]
	}' 'https://test-api.service.hmrc.gov.uk/customs/uk-notice-of-presentation-waiver/authorisations' 
	```

7. Check the response for the authorisation result.

	```json
	{
		"date": "2024-02-01T14:15:22Z",
		"eoris": [
			{
				"eori": "GB123123123000",
				"authorised": true
			}
		]
	}
	```

8. [Get production credentials](https://developer.service.hmrc.gov.uk/developer/applications/add/production).
9. Add a link to your application’s user interface, to call the API when you want to check the authorisation of traders’ NOP waivers.

### User journey

This is a high-level overview of how to use software that has been integrated with the API to check the authorisation of a trader’s NOP waiver.

1. A trader confirms that they would like to use their NOP waiver for a movement.
2. Check if the trader has an NOP waiver.
3. Use the link in the application's UI to verify the trader's NOP waiver authorisation.
5. The trader can now clear the goods between GB and NI.

## Error responses

A detailed description of the error responses for this API can be found in the [Notification of Presentation Waiver Checker API documentation](/api-documentation/docs/api/service/uknw-auth-checker-api/1.0).

## Test data

Any EORI number in the [test-authorised-eoris file](files/test-authorised-eoris.txt) can be used in the sandbox environment to return `authorised = true`.

Valid EORI numbers that are not in the [test-authorised-eoris file](files/test-authorised-eoris.txt) will return `authorised = false` in the sandbox environment. A valid EORI in the false scenario can be any randomly generated EORI number that follows the [EORI number format](#what-is-an-eori-number).

## API rate limiting

Each software house should register a single application with HMRC. This application will be used to identify the software house during the OAuth 2.0 grant flow and will also be used in subsequent per user API calls. We limit the number of requests that each application can make. This protects our backend service against excessive load and encourages real-time API calls over batch processing.

We set limits based on anticipated loads and peaks. Our standard limit is 3 requests per second per application. If you believe that your application will sustain traffic load above this value, contact [SDSTeam@hmrc.gov.uk](mailto:SDSTeam@hmrc.gov.uk).

## Getting help and support

Before contacting us, find out if there is planned API downtime or a technical issue by checking [HMRC API Platform Status](https://api-platform-status.production.tax.service.gov.uk/).

If you have specific questions about the NOP waiver checker API, [contact our Software Developer Support (SDS) Team](https://developer.service.hmrc.gov.uk/developer/support). You'll get an initial response within 2 working days.

You can send queries to [SDSTeam@hmrc.gov.uk](mailto:SDSTeam@hmrc.gov.uk). We may ask for more detailed information.

## Related API documentation

- [NOP waiver checker API reference guide](/api-documentation/docs/api/service/uknw-auth-checker-api/1.0)
- [NOP waiver checker API roadmap](/roadmaps/uknw-auth-checker-api-roadmap/)


## Changelogs

### API changelog

The [NOP API changelog GitHub](https://github.com/hmrc/uknw-auth-checker-api/blob/main/CHANGELOG.md) provides detailed information about API changes.

### Document changelog

**13 August 2024**

- Added [Getting help and support](/guides/uknw-auth-checker-api-service-guide/#getting-help-and-support)
- Added [Related API documentation](/guides/uknw-auth-checker-api-service-guide/#related-api-documentation)

**6 August 2024**

- Edited [Getting started](/guides/uknw-auth-checker-api-service-guide/#getting-started) to include step-by-step instructions for developers
- Added [End-to-end user journeys](/guides/uknw-auth-checker-api-service-guide/#end-to-end-user-journeys) with detailed [developer](/guides/uknw-auth-checker-api-service-guide/#developer-journey-overview) and User journey overviews

**18 July 2024**

- Added [API workflow](/guides/uknw-auth-checker-api-service-guide/#api-workflow)

**11 July 2024**

- Updated [What is an EORI number?](/guides/uknw-auth-checker-api-service-guide/#what-is-an-eori-number)

**24 June 2024**

- Initial draft created
