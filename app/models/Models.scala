package models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID
import scala.collection.Seq

object Models {

  case class AuthRequest(secretKey: String)

  case class Request(value: String)

  case class Respond(items: Seq[Item])

  case class Item(id: UUID, name: String, email: String)

  object AuthRequest {
    implicit val format: OFormat[AuthRequest] = Json.format[AuthRequest]
  }

  object Request {
    implicit val format: OFormat[Request] = Json.format[Request]
  }

  object Respond {
    implicit val format: OFormat[Respond] = Json.format[Respond]
  }

  object Item {
    implicit val format: OFormat[Item] = Json.format[Item]
  }

}
