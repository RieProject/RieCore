package xyz.rieproject.sub.extension

import com.jagrosh.jdautilities.command.CommandEvent

fun CommandEvent.sendImage(directory: String, picture_name: String?) {
    return this.channel.sendFile(
        ClassLoader.getSystemResourceAsStream(directory),
        if (picture_name.isNullOrEmpty()) directory else "$picture_name.png"
    ).queue()
}
