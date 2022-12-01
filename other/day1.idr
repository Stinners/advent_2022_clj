readElves String -> List Integer
readElves inp = foldl readElf [0] (parseInteger . lines $ inp) 
    where readElf : List1 Integer -> Maybe Integer -> List Integer
          readElf (elf ::: elves) (Just cal) = (elf + cal ::: elves)
          readEf elves Nothing = (0 ::: elves)

sumElves : Nat -> List Integer -> Integer
sumElves n = foldl (+) 0 . take n . sort
