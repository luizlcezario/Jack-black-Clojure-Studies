(ns black-jack.game
  (:require [card-ascii-art.core :as card]))

(defn draw-card "generate new rand card"  []
  (inc  (rand-int 13)))


(defn JQK->10 [card]
  (if (> card 10) 10 card))

(defn A->11 [card]
  (if (= card 1) 11 card))

; J Q K is 10 points
; A is 1 or 11 points
(defn points-cards "calculate points of cards" [cards]
  (let [JQKfix (map JQK->10 cards)
        Afix (map A->11 JQKfix)
        sum1 (reduce + JQKfix)
        sum11 (reduce + Afix)]
    (if (> sum11 21) sum1 sum11)))

(defn player
  "create a player"
  [player-name]
  (let  [card1 (draw-card)
         card2 (draw-card)
         cards [card1 card2]
         points (points-cards cards)]
    {:player-name player-name
     :cards cards
     :points points}))

(defn more-card
  "add a new card to player"
  [player]
  (let  [new_player (update player :cards conj (draw-card))
         points (points-cards (:cards new_player))]
    (assoc new_player :points points)))


(defn player_decision? [_]
  (= (read-line) "s"))


(defn dealer-decision? [player-points dealer]
  (let [dealer-points (:points dealer)]
    (and (< dealer-points player-points) (<= player-points 21))))

(defn game [player fn-decision?]
  (println (:player-name player) ": mais carta? (s/n)")
  (if (fn-decision? player)
    (let [new_player (more-card player)]
       (card/print-player new_player)
       (game new_player fn-decision?))))

(defn winner [player1 dealer]
  (let [player1-points (:points player1)
        player2-points (:points dealer)]
    (cond
      (and (<= player1-points 21) (<= player2-points 21))
      (if (> player1-points player2-points) player1 dealer)
      (<= player1-points 21) player1
      :else dealer)))


(def dealer (player "delear"))
(card/print-masked-player dealer)

(def player1 (player "luiz"))
(card/print-player player1)

(game player1 player_decision?)
(game dealer (partial  dealer-decision? (:points player1)))

(def win (winner player1 dealer))
(println "winner is" (:player-name win) " with " (:points win) " points")
