---
title: Notification of Presentation Waiver Checker Service Guide
weight: 1
---
Version 1.0 issued 24 June 2024

# Notification of Presentation (NOP) Waiver Checker Service Guide

This service guide explains how to use the NOP Waiver Checker API. It is written for Community System Providers (CSP's) and software developers to learn about the processes involved in passing Economic Operators Registration and Identification (EORI) numbers to check NOP Waiver validity.

## Overview

This API enables Community System Providers (CSP's) and software developers to check if traders have a valid NOP Waiver authorisation using their EORI number. This waiver is necessary to enable the transit of Not At Risk (NAR) goods moving through from GB-NI. 

The API follows REST principles and has a single POST method endpoint that returns the data in JSON format. It uses standard HTTP error response codes. Use this API to request the NOP Waiver authorisation status of between 1 and 3000 EORI numbers passed as an array. 

**Note:** The API endpoint relates only to Great Britain and Northern Ireland.

### What is an EORI number?

EORI stands for Economic Operators Registration and Identification. It is a unique identification number used by customs authorities across the European Union (EU) to track imports and exports. Any business or individual shipping goods internationally will have an EORI number. This API uses this number to check the authorisation of NOP Waivers. This system replaced the older Trader's Unique Reference Number (TURN) system. For those in the UK, HM Revenue and Customs (HMRC) issues these numbers.

A breakdown of the EORI number format for UK VAT-registered businesses:
- GB: Indicates that the business is based in the UK.
- 205672212: Represents the business's VAT Registration Number.
- 000: These three zones are always added to the end of a UK EORI number.

In summary, having an EORI number is essential for anyone involved in international trade, as it allows customs authorities to monitor and track shipments effectively.

## API Status

This version of the NOP Waiver Checker API:

- supports **only** the NOP Waiver Checker API v1.0
- is currently **not** ready for testing
- will **not** be ready for use in production until the service goes live

Use this API to:

- Request the NOP Waiver authorisation status of 1-3000 EORIs passed as an array.
- Run tests in the HMRC sandbox environment.

## Getting started 

### Developer Setup

To develop using the NOP Waiver Checker API, you must:

- be familiar with HTTP, RESTful services, JSON and OAuth2
- be registered as a developer on the HMRC Developer Hub

You can view all the applications you have currently registered on the Developer Hub Applications page, where you can also administer API subscriptions and application credentials.

### Making API requests

Before sending any requests to the NOP Waiver Checker API, make sure that you are addressing the following points in your software:

- the correct URL for the environment and API version number
- the correct header contents and payload information

For details, see the [NOP Waiver Checker API v1.0 Reference Guide](/api-documentation/docs/api/service/uknw-auth-checker-api/1.0).

The base URLs of the sandbox and production environments are as follows:

```code
Sandbox	https://test-api.service.hmrc.gov.uk/customs/uk-notice-of-presentation-waiver/

Production https://api.service.hmrc.gov.uk/customs/uk-notice-of-presentation-waiver/
```

## Error Responses

A detailed description of the error responses for this API can be found in the [NOP Waiver Checker API v1.0 reference guide](/api-documentation/docs/api/service/uknw-auth-checker-api/1.0).

## API Rate Limiting

Each software house should register a single application with HMRC. This application will be used to identify the software house during the OAuth 2.0 grant flow and will also be used in subsequent per user API calls. We limit the number of requests that each application can make. This protects our backend service against excessive load and encourages real-time API calls over batch processing.

We set limits based on anticipated loads and peaks. Our standard limit is 3 requests per second per application. If you believe that your application will sustain traffic load above this value, contact the SDS Team at email [SDSTeam@hmrc.gov.uk](mailto:SDSTeam@hmrc.gov.uk)
