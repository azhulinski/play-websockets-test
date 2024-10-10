package service

import models.Models.Item

import java.util.UUID
import scala.io.Source
import scala.language.reflectiveCalls

object ReadData {

  def readData: Seq[Item] = {
    for {
      line <- using(Source.fromFile("public/uuid_firstnames_shortemails.csv")) { data =>
        data.getLines().drop(1).toVector
      }
      values = line.split(",").map(_.trim)
    } yield Item(UUID.fromString(values(0)), values(1), values(2))
  }

  private def using[A <: {def close(): Unit}, B](param: A)(f: A => B): B =
    try f(param) finally param.close()

}
