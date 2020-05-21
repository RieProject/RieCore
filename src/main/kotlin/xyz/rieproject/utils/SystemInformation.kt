package xyz.rieproject.utils

import com.jagrosh.jdautilities.commons.JDAUtilitiesInfo
import com.sun.management.OperatingSystemMXBean
import net.dv8tion.jda.api.JDAInfo
import java.lang.management.ManagementFactory
import javax.management.Attribute
import javax.management.ObjectName

class SystemInformation {
    // OS Object
    val osBean = ManagementFactory.getPlatformMXBean(
        OperatingSystemMXBean::class.java)
    val OS = System.getProperty("os.name").toLowerCase()

    // Runtime from JVM
    val freeMemory = Runtime.getRuntime().freeMemory() / 1000000
    val totalMemory = Runtime.getRuntime().totalMemory() / 1000000
    val maxMemory = Runtime.getRuntime().maxMemory() / 1000000

    // Memory used by JVM
    val usageMem = maxMemory - freeMemory

    // Total MEM installed on a machine
    val totalMem = osBean.totalPhysicalMemorySize / 1000000

    // Thread count
    val threadCount = Thread.activeCount()

    // CPU load
    val processCpuLoad: Double
        @Throws(Exception::class)
        get() {
            val mbs = ManagementFactory.getPlatformMBeanServer()
            val name = ObjectName.getInstance("java.lang:type=OperatingSystem")
            val list = mbs.getAttributes(name, arrayOf("ProcessCpuLoad"))

            if (list.isEmpty()) return java.lang.Double.NaN

            val att = list[0] as Attribute
            val value = att.value as Double

            return if (value == -1.0) java.lang.Double.NaN else (value * 1000).toInt() / 10.0
        }

    // Library
    val KOTLIN_VERSION = KotlinVersion.CURRENT.toString()
    val JDA_VERSION = JDAInfo.VERSION
    val JDAU_VERSION = JDAUtilitiesInfo.VERSION
}