package com.example

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import java.io.File
import java.lang.Exception
import javax.imageio.ImageIO

import java.awt.image.BufferedImage
import java.net.URL


class Downloader {
    private val webDriver: WebDriver = ChromeDriver()

    fun download() {

        webDriver.get("https://zno.osvita.ua/mathematics/")
        for (i in 1..15) {
            var webElement: WebElement
            for (j in 1..4) {
                try {
                    webElement = webDriver.findElement(
                        By.xpath("//*[@id=\"main-content\"]/div[1]/ul[$i]/li[$j]/a")
                    )
                    Thread.sleep(5000L)
                    try {
                        createDir(webElement.text)
                        val path = "E:\\Programming\\kotlinProjects\\ZnoQuestionDownloader\\src\\" + webElement?.text
                        webElement?.click()
                        webElement = webDriver.findElement(By.xpath("//*[@id=\"q1\"]/div[1]"))
                        val numberOfQuestion = webElement.text.substringAfterLast(' ').toInt()
                        for (k in 1..numberOfQuestion) {
                            try {
                                Thread.sleep(500L)
                                var element = webDriver.findElement(By.xpath("//*[@id=\"q_form_$k\"]/div[1]/p/img"))
                                downloadImg(k, element.getAttribute("src"), path)
                                element = webDriver.findElement(By.xpath("//*[@id=\"tasks-numbers\"]/span[$k]"))
                                element.click()
                            } catch (e: Exception) {
                                println("can't move next")
                            }
                        }
                    } catch (e: Exception) {
                        println("can't follow the url")
                    }
                    webDriver.navigate().back()
                } catch (e: Exception) {
                    println("Can't find zno test")
                }
            }
        }
    }

    private fun downloadImg(n: Int, url: String, path: String?) {
        val img: BufferedImage = ImageIO.read(URL(url))
        val file = File("$path\\question$n.png")
        ImageIO.write(img, "png", file)
    }

    private fun createDir(name: String?) {
        val dir = File("E:\\Programming\\kotlinProjects\\ZnoQuestionDownloader\\src\\$name").mkdirs()
        if (dir) println("Directory path was created successfully")
        else println("Failed to create directory path")
    }
}