package Actors

import akka.actor.{Actor, ActorRef, Props}
import models.Models._

import play.api.Logger
import play.api.libs.json.{JsValue, Json}
import service.ReadData.readData

class WebSocketActor(actorRef: ActorRef) extends Actor {

  private val dataSet = readData


  private var isAuthenticated = false
  private val secretKey = "hardcoded_secret_key"

  val logger: Logger = play.api.Logger(getClass)
  logger.info(s"WebSocketActor class started")

  def receive: Receive = {

    case jsValue: JsValue =>

      if (!isAuthenticated) {
        jsValue.validate[AuthRequest].map { authRequest =>
          if (authRequest.secretKey == secretKey) {
            isAuthenticated = true
            logger.info("Authenticated")
          }
        }.recoverTotal { _ =>
          logger.warn("Authentication failed")
        }
      } else {
        jsValue.validate[Request].map { request: Request =>
          val matchingItems = dataSet.filter(item => item.name == request.value || item.email == request.value).take(3)
          val response = Respond(matchingItems)
          actorRef ! Json.toJson(response)
        }.recoverTotal { _ =>
          logger.warn("Invalid request")
        }
      }
  }
}

object WebSocketActor {
  def props(clientActorRef: ActorRef): Props = Props(new WebSocketActor(clientActorRef))
}