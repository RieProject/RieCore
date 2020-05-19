package xyz.rieproject.utils

import java.util.*
import javax.management.Attribute
import javax.management.ObjectName
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.ThreadContext
import xyz.jdev.utils.Javachalk
import java.lang.management.ManagementFactory

class CConsole(pid: String?, source: Class<Any>?) {
    var pid = pid
    var timestamp = Date()
    var thread = Thread.activeCount()
    var cpuload = processCpuLoad
    val ntimestamp: String = Javachalk.cyan(timestamp.toString())
    val npid: String = Javachalk.cyan(pid.toString())
    val sfl4jlogger = LogManager.getLogger(source)
    init {
        val npid = pid!!.replace("@.*".toRegex(), "")
        ThreadContext.put("pid", npid)
    }
    fun getLogger(): Logger { return sfl4jlogger }

    companion object {
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
    }
}