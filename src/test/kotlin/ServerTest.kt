package com.fspm.docs

import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.*
import io.ktor.http.contentType
import io.ktor.client.statement.bodyAsText

class ServerTest {

    @Test
    fun `test root endpoint`() = testApplication {
        // loads default configuration
        configure()
        // verify server root returns 200
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `test static page endpoint`() = testApplication {
        configure()
        val response = client.get("/content/sample.html")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("html", response.contentType()?.contentSubtype)
        assertContains(response.bodyAsText(), "This page is built with:")
    }

    @Test
    fun `test illegal state handler`() = testApplication {
        configure()
        val response = client.get("/error")
        assertEquals(HttpStatusCode.InternalServerError, response.status)
        assertContains(response.bodyAsText(), "Too Busy")
    }
}
