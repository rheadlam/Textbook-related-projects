"""This module relates to a Think Python, 2nd Edition case study.
It prints how many hands on average are dealt before achieving a specific winning 
hand in poker.
"""

from __future__ import print_function, division

from Card import Hand, Deck


class Hist(dict):
    """An item(x)-frequency mapped histogram."""

    def __init__(self, seq=[]):
        "Creates histogram beginning with the items x in seq."
        for x in seq:
            self.count(x)

    def count(self, x, f=1):
        "Increments/decrements counter associated with item x."
        self[x] = self.get(x, 0) + f
        if self[x] == 0:
            del self[x]


class PokerHand(Hand):
    """Represents poker hand."""

    all_labels = ['straightflush', 'fourkind', 'fullhouse', 'flush',
                  'straight', 'threekind', 'twopair', 'pair', 'highcard']

    def make_histograms(self):
        """Computes histograms for suits and ranks.

        Creates attributes:

          suits: histogram of suits in hand.
          ranks: histogram of ranks.
          sets: sorted list of rank sets in hand.
        """
        self.suits = Hist()
        self.ranks = Hist()
        
        for c in self.cards:
            self.suits.count(c.suit)
            self.ranks.count(c.rank)

        self.sets = list(self.ranks.values())
        self.sets.sort(reverse=True)
        
    def check_sets(self, *t):
        """Checks self.sets contains sets meeting requirements in t.

        t: int list
        """
        for need, have in zip(t, self.sets):
            if need > have:
                return False
        return True
    
    def in_a_row(self, ranks, n=5):
        """Checks histogram has n ranks in a row.

        hist: rank-frequency mapping
        n: required number
        """
        count = 0
        for i in range(1, 15):
            if ranks.get(i, 0):
                count += 1
                if count == n:
                    return True
            else:
                count = 0
        return False

    def has_highcard(self):
        """Returns True for hand with high card."""
        return len(self.cards)
    
    def has_pair(self):
        """Checks hand for pair."""
        return self.check_sets(2)
        
    def has_twopair(self):
        """Checks hand for two pair."""
        return self.check_sets(2, 2)
        
    def has_threekind(self):
        """Checks hand for three of a kind."""
        return self.check_sets(3)
        
    def has_fourkind(self):
        """Checks hand for four of a kind."""
        return self.check_sets(4)

    def has_fullhouse(self):
        """Checks hand for full house."""
        return self.check_sets(3, 2)

    def has_flush(self):
        """Checks hand for flush."""
        for val in self.suits.values():
            if val >= 5:
                return True
        return False

    def has_straight(self):
        """Checks hand for straight."""

        ranks = self.ranks.copy()
        ranks[14] = ranks.get(1, 0)

        return self.in_a_row(ranks, 5)
                
    def has_straightflush(self):
        """Checks hand for straight flush.
        """
        d = {}
        for c in self.cards:
            d.setdefault(c.suit, PokerHand()).add_card(c)

        for hand in d.values():
            if len(hand.cards) < 5:
                continue            
            hand.make_histograms()
            if hand.has_straight():
                return True
        return False

    def classify(self):
        """Classifies hand.

        Creates attributes:
          labels
        """
        self.make_histograms()

        self.labels = []
        for label in PokerHand.all_labels:
            f = getattr(self, 'has_' + label)
            if f():
                self.labels.append(label)


class PokerDeck(Deck):
    """Represents deck for dealing poker hands."""

    def deal_hands(self, num_cards=5, num_hands=10):
        """Deals hands from deck and returns Hands.

        num_cards: cards in hand
        num_hands: number of hands

        returns: list of Hands
        """
        hands = []
        for i in range(num_hands):        
            hand = PokerHand()
            self.move_cards(hand, num_cards)
            hand.classify()
            hands.append(hand)
        return hands


def main():

    hist = Hist()

    n = 1000
    for i in range(n):
        if i % 100 == 0:
            print(i)
            
        deck = PokerDeck()
        deck.shuffle()

        hands = deck.deal_hands(7, 7)
        for hand in hands:
            for label in hand.labels:
                hist.count(label)
            
    total = 7.0 * n
    print(total, 'hands dealt:')

    for label in PokerHand.all_labels:
        freq = hist.get(label, 0)
        if freq == 0: 
            continue
        p = total / freq
        print('On average %s occurs in %.2f hands.' % (label, p))

        
if __name__ == '__main__':
    main()

