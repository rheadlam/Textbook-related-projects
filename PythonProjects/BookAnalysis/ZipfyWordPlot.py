"""This module relates to a Think Python, 2nd Edition case study.
It plots or prints frequency against rank to demonstrate zipf's law. That is -
given some corpus of natural language utterances, the frequency of any 
word is inversely proportional to its rank in the frequency table. By default 
Flatland is analysed once again, and is flagged to plot. Running as a script 
allows filename and flag arguments to be entered by user.
"""

from __future__ import print_function, division

import sys
import matplotlib.pyplot as plt #needs installation 
from WordHistogram import process_file

def rank_freq(hist):
    """Returns (rank, freq) tuples in a list.

    hist: histogram mapping words to corresponding frequency

    returns: list of (rank, freq) tuples
    """
    # sorts list in decreasing order
    freqs = list(hist.values())
    freqs.sort(reverse=True)

    # enumerates ranks and frequencies 
    rf = [(r+1, f) for r, f in enumerate(freqs)]
    return rf


def print_ranks(hist):
    """Prints rank against frequency.

    hist: histogram mapping words to corresponding frequency
    """
    for r, f in rank_freq(hist):
        print(r, f)


def plot_ranks(hist, scale='log'):
    """Plots frequency against rank.

    hist: histogram mapping words to corresponding frequency
    scale: string 'linear' or 'log'
    """
    t = rank_freq(hist)
    rs, fs = zip(*t)

    plt.clf()
    plt.xscale(scale)
    plt.yscale(scale)
    plt.title('Zipf Word plot')
    plt.xlabel('Rank')
    plt.ylabel('Frequency')
    plt.plot(rs, fs, 'r-', linewidth=3)
    plt.show()


def main(script, filename='flatland.txt', flag='plot'):
    hist = process_file(filename, skip_header=True)
    # prints or plots
    if flag == 'print':
        print_ranks(hist)
    elif flag == 'plot':
        plot_ranks(hist)
    else:
        print('Usage: ZipfyWordPlot.py filename [print|plot]') #remove square brackets. 

if __name__ == '__main__':
    main(*sys.argv)
