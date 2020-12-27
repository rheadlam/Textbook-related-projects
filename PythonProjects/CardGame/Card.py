"""This module relates to a Think Python, 2nd Edition case study.
It prints a hand of playing cards from a shuffled deck utilising OOP.
"""

from __future__ import print_function, division

import random

class Card:
    """Represents a standard playing card.
    
    Attributes:
      suit: int 0-3
      rank: int 1-13
    """

    suit_names = ["Clubs", "Diamonds", "Hearts", "Spades"]
    rank_names = [None, "Ace", "2", "3", "4", "5", "6", "7", 
              "8", "9", "10", "Jack", "Queen", "King"]

    def __init__(self, suit=0, rank=2):
        self.suit = suit
        self.rank = rank

    def __str__(self):
        """Returns string representation of card."""
        return '%s of %s' % (Card.rank_names[self.rank],
                             Card.suit_names[self.suit])

    def __eq__(self, other):
        """Checks equivalence of two cards.
        
        self, other: Card 

        returns: boolean
        """
        return self.suit == other.suit and self.rank == other.rank

    def __lt__(self, other):
        """Compares cards.
        
        self, other: Card

        returns: boolean
        """
        t1 = self.suit, self.rank
        t2 = other.suit, other.rank
        return t1 < t2


class Deck:
    """Represents a deck of cards.

    Attributes:
      cards: list of Card objects.
    """
    
    def __init__(self):
        """Initializes Deck with 52 cards.
        """
        self.cards = []
        for suit in range(4):
            for rank in range(1, 14):
                card = Card(suit, rank)
                self.cards.append(card)

    def __str__(self):
        """Returns string representation of deck.
        """
        res = []
        for card in self.cards:
            res.append(str(card))
        return '\n'.join(res)

    def add_card(self, card):
        """Adds card to deck.

        card: Card
        """
        self.cards.append(card)

    def remove_card(self, card):
        """Removes card from deck or raises exception if cannot.
        
        card: Card
        """
        self.cards.remove(card)

    def pop_card(self, i=-1):
        """Removes and returns card from the deck.

        i: index of card to pop. default pops last card.
        """
        return self.cards.pop(i)

    def shuffle(self):
        """Shuffles cards in deck."""
        random.shuffle(self.cards)

    def sort(self):
        """Sorts cards in ascending order."""
        self.cards.sort()

    def move_cards(self, hand, num):
        """Moves num-number of cards from deck to Hand.

        hand: Hand object
        num: int number cards to move
        """
        for i in range(num):
            hand.add_card(self.pop_card())

class Hand(Deck):
    """Represents a hand of playing cards."""
    
    def __init__(self, label=''):
        self.cards = []
        self.label = label

if __name__ == '__main__':
    deck = Deck()
    deck.shuffle()

    hand = Hand()

    deck.move_cards(hand, 5)
    hand.sort()
    print(hand)
