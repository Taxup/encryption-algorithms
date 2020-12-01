package com.example

import RandomString
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.security.SecureRandom

data class User(val email:String, val hashedPassword:String, val salt:String)

object Users: Table() {
    val email: Column<String> = varchar("email",255).uniqueIndex()
    val hash: Column<String> = varchar("hash",255)
    val salt: Column<String> = varchar("salt",255)

    fun toUser(row: ResultRow):User = User(email = row[email], hashedPassword = row[hash], salt = row[salt])
}

fun main() {
//    localhost:8080/login
    embeddedServer(Netty, port = 8080, module = Application::module).start(true)
}

fun Application.module() {

    Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
    transaction {
        SchemaUtils.create(Users)
    }

    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    install(Routing) {
        route("/login") {
            get {
                call.respond(FreeMarkerContent("login.ftl", null))
            }
            post {
                val post = call.receiveParameters()
                val email = post["email"].toString()
                val password = post["password"].toString()

                val regex =
                    Regex(pattern = "^(?=.*[A-Z])(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*#?&])[A-Za-z0-9@$!%*#?&]{8,}$")
                println(post["btn"].toString())

                if (regex.containsMatchIn(password) && email.length > 4) {

                    if (post["btn"] == "register") {
                        var bool = false

                        transaction {
                            Users.select { Users.email eq email }.forEach { _ ->
                                bool = true
                            }
                        }

                        if (bool) {
                            call.respondText { "Account with this email already exists" }
                        } else {
                            val easy = RandomString.alphanum
                            val randomSalt = RandomString(10, SecureRandom(), easy).nextString()
                            println(password + randomSalt)
                            transaction {
                                Users.insert {
                                    it[Users.email] = email
                                    it[salt] = randomSalt
                                    it[hash] = hash(hash(password + randomSalt).toString()).toString()
                                }
                            }

                            call.respondText { "Account created with salt - $randomSalt" }
                        }

                    } else if (post["btn"] == "login") {
                        var bool = false

                        transaction {
                            Users.select { Users.email eq email }.forEach {
                                if (it[Users.hash].toString() == hash(hash(password + it[Users.salt]).toString()).toString()) {
                                    bool = true
                                }
                            }
                        }

                        if (bool) {
                            call.respondText { "You successful logged in!" }
                        } else {
                            call.respondText { "Wrong email or password" }
                        }
                    }
                } else {
                    if (post["btn"] == "list") {
                        val listOfUsers = transaction {
                            Users.selectAll().map { Users.toUser(it) }
                        }
                        val list = listOfUsers.toString()
                        call.respondText { list }
                    } else {
                        call.respondText { "Enter correct format of password!\nYour password - $password  \nEmail - $email" }
                    }
                }
            }
        }
    }
}


fun hash(string: String):String? {
    val bytes = string.toByteArray()
    val builder = StringBuilder()
    for (i in bytes.indices) {
        val binary =
            String.format("%8s", Integer.toBinaryString(bytes[i].toInt())).replace(" ", "0")
        builder.append(binary)
    }
    val convertBinary = builder.toString()
    val txtLength = string.length * 8 - 8
    val endLength = txtLength(txtLength + 8)
    var t = txtLength % 512
    t = if (432 - t < 0) {
        val tx = 512 - t
        tx + 504 + t
    } else {
        432 - t
    }
    val txtBinary = StringBuilder(convertBinary)
    txtBinary.insert(txtBinary.toString().length, "10000000")
    while (t > 0) {
        txtBinary.insert(txtBinary.toString().length, 0)
        t--
    }
    txtBinary.insert(txtBinary.toString().length, endLength)
    var textprint = txtPrint(txtBinary.toString())
    textprint = textprint.replace(" ", "")
    return hashSHA(textprint)
}

fun circularShift(x: Int, n: Int): Int {
    return x shl n or (x ushr 32 - n)
}

fun txtLength(length: Int): String {
    val bitsLength = Integer.toBinaryString(length)
    val builder = StringBuilder(bitsLength)
    var t = 64 - bitsLength.length
    while (t > 0) {
        builder.insert(0, 0)
        t--
    }
    return builder.toString()
}

fun txtPrint(txtBinary: String): String {
    val builder = StringBuilder(txtBinary)
    var length = txtBinary.length
    while (length > 0) {
        if (length % 32 == 0) {
            builder.insert(length, " ")
        }
        length--
    }
    return builder.toString()
}
private const val A = 0x67452301; private const val B = -0x10325477; private const val C = -0x67452302; private const val D = 0x10325476; private const val E = -0x3c2d1e10
private var h1 = A; private var h2 = B; private var h3 = C; private var h4 = D; private var h5 = E
fun hashSHA(finalText: String): String? {
    val chunks = IntArray(finalText.length / 32)
    run {
        var i = 0
        while (i < finalText.length) {
            chunks[i / 32] = Integer.valueOf(finalText.substring(i + 1, i + 32), 2)
            if (finalText[i] == '1') {
                chunks[i / 32] = chunks[i / 32] or -0x80000000
            }
            i += 32
        }
    }
    var k: Int
    val w = IntArray(80)
    run {
        var i = 0
        while (i < chunks.size) {
            k = 0
            while (k < 16) {
                w[k] = chunks[k + i]
                k++
            }
            k = 16
            while (k <= 79) {
                val wW = w[k - 3] xor w[k - 8] xor w[k - 14] xor w[k - 16]
                w[k] = circularShift(wW, 1)
                k++
            }
            i += 16
        }
    }
    var a = h1; var b = h2; var c = h3; var d = h4; var e = h5
    var t: Int
    var temp: Int
    var k1: Int
    for (i in 0..79) {
        when {
            i < 20 -> {
                t = b and c or (b.inv() and d)
                k1 = 0x5A827999
            }
            i <= 39 -> {
                t = b xor c xor d
                k1 = 0x6ED9EBA1
            }
            i <= 59 -> {
                t = b and c or (b and d) or (c and d)
                k1 = -0x70e44324
            }
            else -> {
                t = b xor c xor d
                k1 = -0x359d3e2a
            }
        }
        temp = circularShift(a, 5) + t + e + w[i] + k1
        e = d; d = c; c = circularShift(b, 30); b = a ; a = temp
    }
    h1 += a;h2 += b;h3 += c;h4 += d;h5 += e
    val h1Res = Integer.toHexString(h1);val h2Res = Integer.toHexString(h2);val h3Res = Integer.toHexString(h3);val h4Res = Integer.toHexString(h4);val h5Res = Integer.toHexString(h5)
    h1=A;h2=B;h3=C;h4=D;h5=E
    return h1Res + h2Res + h3Res + h4Res + h5Res
}
