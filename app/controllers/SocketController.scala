package controllers

import Actors.WebSocketActor
import akka.actor.ActorSystem
import play.api.Logger
import play.api.libs.json.JsValue
import play.api.libs.streams.ActorFlow
import play.api.mvc.{AbstractController, ControllerComponents, WebSocket}

import javax.inject.{Inject, Singleton}

@Singleton
class SocketController @Inject()(cc: ControllerComponents)(implicit system: ActorSystem)
  extends AbstractController(cc) {

  val logger: Logger = play.api.Logger(getClass)

  def socket: WebSocket = WebSocket.accept[JsValue, JsValue] { requestHeader =>
    logger.info("'socket' function is called")

    ActorFlow.actorRef { actorRef =>
      logger.info("socket: calling My Actor")
      WebSocketActor.props(actorRef)
    }
  }

}
