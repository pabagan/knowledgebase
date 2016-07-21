import unittest
from alphabet_soup import alphabet_soup

class Test(unittest.TestCase):
    def test_alphabet_soup_OK(self):
        is_doable =alphabet_soup('KANGAROO', 'OORAGNAK')
        self.assertEqual(is_doable, True)

    def test_alphabet_soup_KO(self):
        is_doable =alphabet_soup('YES', 'NOT')
        self.assertEqual(is_doable, False)

    def test_arguments_KO(self):
        is_doable =alphabet_soup('lowercase', '123')
        self.assertEqual(is_doable, False)

if __name__ == '__main__':
    unittest.main()