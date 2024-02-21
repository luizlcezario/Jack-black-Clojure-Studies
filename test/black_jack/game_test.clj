(ns black-jack.game-test
  (:require [clojure.test :refer :all])
  (:require [black-jack.game :refer [points-cards]]))

(deftest points-cards-test
  (testing "testing rule of A"
    (is (= 21 (points-cards [1 10 10]))))
  (testing "testing rule of A with 2 A"
    (is (= 2 (points-cards [1 1]))))
  )
