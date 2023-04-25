# Strategy

1. iterate over a wordlist
2. find all fields starting with current first letter, create `Path` instance for each
3. navigate into all directions. If movement is valid, check if letter at this position matches
4. if no matching letter was found, backtrack.
5. remember the longest match to have a clue if checking the next word makes any sense. (If next word starts with current longest match + 1 letters, we won't find any match)