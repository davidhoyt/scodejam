package scodejam

import scala.language.reflectiveCalls
import scala.collection.mutable
import java.nio.file.{Path, FileSystems}
import java.util.{Calendar, Date}

object Utils {
  def using[A <: {def close(): Unit}, B](param: A)(f: A => B): B =
    try {
      f(param)
    } finally {
      param.close()
    }

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    using(new java.io.PrintWriter(f)) { p =>
      op(p)
    }
  }

  def zipProject(): Unit = {
    zipProject("dhoyt-google-codejam-" + (Calendar.getInstance().get(Calendar.YEAR)) + ".zip")
  }

  def zipProject(out: String, directory: String = "."): Unit = {
    zipProject(out, directory, Set("codejam/inputs/", "codejam/outputs/", ".git/", ".idea/", "target/"), Set("README.md","scodejam.iml", ".gitignore"))
  }

  def zipProject(out: String, directory: String, exclusionsStartsWith: Iterable[String], exclusionsEndsWith: Iterable[String]): Unit = {
    import java.io.{ BufferedInputStream, FileInputStream, FileOutputStream, File }
    import java.util.zip.{ ZipEntry, ZipOutputStream }

    val dir = new File(directory)
    if (!dir.exists())
      throw new IllegalArgumentException("Please ensure that \"" + directory + "\" exists before attempting to zip it up")

    val out_path = FileSystems.getDefault().getPath(out)
    val dir_path = FileSystems.getDefault().getPath(dir.getAbsolutePath())

    var read: Int = 0
    val buffer: Array[Byte] = Array.ofDim[Byte](4096)
    val stack = mutable.Stack(dir)

    using(new ZipOutputStream(new FileOutputStream(out))) { zip =>
      while(!stack.isEmpty) {
        val directory = stack.pop();
        directory.listFiles().foreach { f =>
          val f_path = dir_path.relativize(FileSystems.getDefault().getPath(f.getAbsolutePath()))
          if (out_path != f_path && !exclusionsStartsWith.exists(f_path.startsWith(_)) && !exclusionsEndsWith.exists(f_path.endsWith(_))) {
            if (!f.isDirectory) {
              zip.putNextEntry(new ZipEntry(f_path.toString))
              using(new FileInputStream(f)) { fin =>
                read = fin.read(buffer)
                while(read > 0) {
                  zip.write(buffer, 0, read)
                  read = fin.read(buffer)
                }
              }
              zip.closeEntry()
            } else {
              stack.push(f)
            }
          }
        }
      }
    }
  }
}
