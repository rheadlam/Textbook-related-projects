"""This module relates to a Think Python, 2nd Edition case study.
It prints the total number of words, the total number of different words, lists
word-frequency pairs descending in frequency, words not matching a second .txt file
"words" and displays random words. A .txt file downlaoded from gutenberg is analysed,
I chose the book flatland:a romance of many dimensions.
"""

from __future__ import print_function, division

import string
import random

def process_file(file, skip_header):
    """Creates histogram containing words from a file.

    file: string
    skip_header: boolean, to skip header
   
    returns: words mapped to occurring frequency.
    """
    hist = {}
    fp = open(file)

    if skip_header:
        skip_file_header(fp)

    for line in fp:
        if line.startswith('*** END OF THIS'):
            break

        process_line(line, hist)

    return hist


def skip_file_header(fp):
    """Reads from fp until line ending header.

    fp: open file object
    """
    for line in fp:
        if line.startswith('*** START OF THIS'):
            break


def process_line(line, hist):
    """Adds words in line to histogram.

    Modifies hist.

    line: string
    hist: histogram mapping words to coreesponding frequency
    """

    line = line.replace('-', ' ')
    strippables = string.punctuation + string.whitespace

    for word in line.split():
        
        word = word.strip(strippables)
        word = word.lower()

        hist[word] = hist.get(word, 0) + 1

def total_words(hist):
    """Returns the total of the frequencies in a histogram."""
    return sum(hist.values())

def different_words(hist):
    """Returns the number of different words in a histogram."""
    return len(hist)

def most_frequent(hist):
    """Creates word-frequency pairs in descending order of frequencies.

    hist: histogram mapping words to corresponding frequency

    returns: list of frequency-word pairs
    """
    list_of_pairs = []
    for key, value in hist.items():
        list_of_pairs.append((value, key))

    list_of_pairs.sort()
    list_of_pairs.reverse()
    return list_of_pairs

def print_most_frequent_words(hist, num=10):
    """Prints most frequent words with corresponding frequency
     from histogram.
    
    hist: histogram mapping words to occuring frequencies)
    num: number of words that will be printed
    """
    t = most_frequent(hist)
    print('\nMost frequent words:')
    for freq, word in t[:num]:
        print(word, '\t', freq)
        
def random_word(hist):
    """Chooses a random word from a histogram.

    The probability of each word is proportional to its frequency.
    """
    t = []
    for word, freq in hist.items():
        t.extend([word] * freq)

    return random.choice(t)

def print_random_words(hist, num=5):
    print("\nRandom words from flatland:")
    for i in range(num):
        print(random_word(hist), end=' ')        

def subtract(d1, d2, num=15):
    """Subtracts keys of d2 from keys of d1.

    d1, d2: dictionaries
    num: number of words that will be printed
    
    returns: dictionary with all the keys in d1 not in d2
    """
    res = {}
    for key in d1:
        if key not in d2:
            res[key] = None
            if len(res) == num:
                break
    return res

def print_diff_words(hist1, hist2):
    """Prints words in flatland.txt not in words.txt.
    
    hist1: word-frequency histogram from flatland.txt
    hist2: word-frequency histogram from words.txt 
    """
    t = subtract(hist1, hist2)
    print("\n\nWords in flatland.txt not in words.txt:")
    for word in t.keys():
        print(word,end=' ')

def main():
    hist = process_file('flatland.txt', skip_header=True)
    words = process_file('words.txt', skip_header=False)
 
    print('Total N words in Flatland:', total_words(hist))
    print('\nN different words in Flatland:', different_words(hist))
    print_most_frequent_words(hist)
    print_random_words(hist)
    print_diff_words(hist, words)    

if __name__ == '__main__':
    main()


