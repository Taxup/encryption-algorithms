using System;
using System.Linq;
using System.Text;

namespace InformationSecurity
{
    internal class AES
    {
        private static readonly string[,] Sbox = new string[16, 16]
        {
            {"63", "7C", "77", "7B", "F2", "6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76"},
            {"CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "D4", "A2", "AF", "9C", "A4", "72", "C0"},
            {"B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15"},
            {"04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75"},
            {"09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84"},
            {"53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF"},
            {"D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8"},
            {"51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2"},
            {"CD", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73"},
            {"60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB"},
            {"E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79"},
            {"E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08"},
            {"BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A"},
            {"70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E"},
            {"E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF"},
            {"8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16"}
        };

        private static readonly string[,] InverseSbox = new string[16, 16]
        {
            {"52", "09", "6a", "d5", "30", "36", "a5", "38", "bf", "40", "a3", "9e", "81", "f3", "d7", "fb"},
            {"7c", "e3", "39", "82", "9b", "2f", "ff", "87", "34", "8e", "43", "44", "c4", "de", "e9", "cb"},
            {"54", "7b", "94", "32", "a6", "c2", "23", "3d", "ee", "4c", "95", "0b", "42", "fa", "c3", "4e"},
            {"08", "2e", "a1", "66", "28", "d9", "24", "b2", "76", "5b", "a2", "49", "6d", "8b", "d1", "25"},
            {"72", "f8", "f6", "64", "86", "68", "98", "16", "d4", "a4", "5c", "cc", "5d", "65", "b6", "92"},
            {"6c", "70", "48", "50", "fd", "ed", "b9", "da", "5e", "15", "46", "57", "a7", "8d", "9d", "84"},
            {"90", "d8", "ab", "00", "8c", "bc", "d3", "0a", "f7", "e4", "58", "05", "b8", "b3", "45", "06"},
            {"d0", "2c", "1e", "8f", "ca", "3f", "0f", "02", "c1", "af", "bd", "03", "01", "13", "8a", "6b"},
            {"3a", "91", "11", "41", "4f", "67", "dc", "ea", "97", "f2", "cf", "ce", "f0", "b4", "e6", "73"},
            {"96", "ac", "74", "22", "e7", "ad", "35", "85", "e2", "f9", "37", "e8", "1c", "75", "df", "6e"},
            {"47", "f1", "1a", "71", "1d", "29", "c5", "89", "6f", "b7", "62", "0e", "aa", "18", "be", "1b"},
            {"fc", "56", "3e", "4b", "c6", "d2", "79", "20", "9a", "db", "c0", "fe", "78", "cd", "5a", "f4"},
            {"1f", "dd", "a8", "33", "88", "07", "c7", "31", "b1", "12", "10", "59", "27", "80", "ec", "5f"},
            {"60", "51", "7f", "a9", "19", "b5", "4a", "0d", "2d", "e5", "7a", "9f", "93", "c9", "9c", "ef"},
            {"a0", "e0", "3b", "4d", "ae", "2a", "f5", "b0", "c8", "eb", "bb", "3c", "83", "53", "99", "61"},
            {"17", "2b", "04", "7e", "ba", "77", "d6", "26", "e1", "69", "14", "63", "55", "21", "0c", "7d"}
        };

        private static string[] by2 =
        {
            "00", "02", "04", "06", "08", "0a", "0c", "0e", "10", "12", "14", "16", "18", "1a", "1c", "1e", "20", "22",
            "24", "26", "28", "2a", "2c", "2e", "30", "32", "34", "36", "38", "3a", "3c", "3e", "40", "42", "44", "46",
            "48", "4a", "4c", "4e", "50", "52", "54", "56", "58", "5a", "5c", "5e", "60", "62", "64", "66", "68", "6a",
            "6c", "6e", "70", "72", "74", "76", "78", "7a", "7c", "7e", "80", "82", "84", "86", "88", "8a", "8c", "8e",
            "90", "92", "94", "96", "98", "9a", "9c", "9e", "a0", "a2", "a4", "a6", "a8", "aa", "ac", "ae", "b0", "b2",
            "b4", "b6", "b8", "ba", "bc", "be", "c0", "c2", "c4", "c6", "c8", "ca", "cc", "ce", "d0", "d2", "d4", "d6",
            "d8", "da", "dc", "de", "e0", "e2", "e4", "e6", "e8", "ea", "ec", "ee", "f0", "f2", "f4", "f6", "f8", "fa",
            "fc", "fe", "1b", "19", "1f", "1d", "13", "11", "17", "15", "0b", "09", "0f", "0d", "03", "01", "07", "05",
            "3b", "39", "3f", "3d", "33", "31", "37", "35", "2b", "29", "2f", "2d", "23", "21", "27", "25", "5b", "59",
            "5f", "5d", "53", "51", "57", "55", "4b", "49", "4f", "4d", "43", "41", "47", "45", "7b", "79", "7f", "7d",
            "73", "71", "77", "75", "6b", "69", "6f", "6d", "63", "61", "67", "65", "9b", "99", "9f", "9d", "93", "91",
            "97", "95", "8b", "89", "8f", "8d", "83", "81", "87", "85", "bb", "b9", "bf", "bd", "b3", "b1", "b7", "b5",
            "ab", "a9", "af", "ad", "a3", "a1", "a7", "a5", "db", "d9", "df", "dd", "d3", "d1", "d7", "d5", "cb", "c9",
            "cf", "cd", "c3", "c1", "c7", "c5", "fb", "f9", "ff", "fd", "f3", "f1", "f7", "f5", "eb", "e9", "ef", "ed",
            "e3", "e1", "e7", "e5"
        };

        private static string[] by3 =
        {
            "00", "03", "06", "05", "0c", "0f", "0a", "09", "18", "1b", "1e", "1d", "14", "17", "12", "11", "30", "33",
            "36", "35", "3c", "3f", "3a", "39", "28", "2b", "2e", "2d", "24", "27", "22", "21", "60", "63", "66", "65",
            "6c", "6f", "6a", "69", "78", "7b", "7e", "7d", "74", "77", "72", "71", "50", "53", "56", "55", "5c", "5f",
            "5a", "59", "48", "4b", "4e", "4d", "44", "47", "42", "41", "c0", "c3", "c6", "c5", "cc", "cf", "ca", "c9",
            "d8", "db", "de", "dd", "d4", "d7", "d2", "d1", "f0", "f3", "f6", "f5", "fc", "ff", "fa", "f9", "e8", "eb",
            "ee", "ed", "e4", "e7", "e2", "e1", "a0", "a3", "a6", "a5", "ac", "af", "aa", "a9", "b8", "bb", "be", "bd",
            "b4", "b7", "b2", "b1", "90", "93", "96", "95", "9c", "9f", "9a", "99", "88", "8b", "8e", "8d", "84", "87",
            "82", "81", "9b", "98", "9d", "9e", "97", "94", "91", "92", "83", "80", "85", "86", "8f", "8c", "89", "8a",
            "ab", "a8", "ad", "ae", "a7", "a4", "a1", "a2", "b3", "b0", "b5", "b6", "bf", "bc", "b9", "ba", "fb", "f8",
            "fd", "fe", "f7", "f4", "f1", "f2", "e3", "e0", "e5", "e6", "ef", "ec", "e9", "ea", "cb", "c8", "cd", "ce",
            "c7", "c4", "c1", "c2", "d3", "d0", "d5", "d6", "df", "dc", "d9", "da", "5b", "58", "5d", "5e", "57", "54",
            "51", "52", "43", "40", "45", "46", "4f", "4c", "49", "4a", "6b", "68", "6d", "6e", "67", "64", "61", "62",
            "73", "70", "75", "76", "7f", "7c", "79", "7a", "3b", "38", "3d", "3e", "37", "34", "31", "32", "23", "20",
            "25", "26", "2f", "2c", "29", "2a", "0b", "08", "0d", "0e", "07", "04", "01", "02", "13", "10", "15", "16",
            "1f", "1c", "19", "1a"
        };

        private static string[] by9 =
        {
            "00", "09", "12", "1b", "24", "2d", "36", "3f", "48", "41", "5a", "53", "6c", "65", "7e", "77", "90", "99",
            "82", "8b", "b4", "bd", "a6", "af", "d8", "d1", "ca", "c3", "fc", "f5", "ee", "e7", "3b", "32", "29", "20",
            "1f", "16", "0d", "04", "73", "7a", "61", "68", "57", "5e", "45", "4c", "ab", "a2", "b9", "b0", "8f", "86",
            "9d", "94", "e3", "ea", "f1", "f8", "c7", "ce", "d5", "dc", "76", "7f", "64", "6d", "52", "5b", "40", "49",
            "3e", "37", "2c", "25", "1a", "13", "08", "01", "e6", "ef", "f4", "fd", "c2", "cb", "d0", "d9", "ae", "a7",
            "bc", "b5", "8a", "83", "98", "91", "4d", "44", "5f", "56", "69", "60", "7b", "72", "05", "0c", "17", "1e",
            "21", "28", "33", "3a", "dd", "d4", "cf", "c6", "f9", "f0", "eb", "e2", "95", "9c", "87", "8e", "b1", "b8",
            "a3", "aa", "ec", "e5", "fe", "f7", "c8", "c1", "da", "d3", "a4", "ad", "b6", "bf", "80", "89", "92", "9b",
            "7c", "75", "6e", "67", "58", "51", "4a", "43", "34", "3d", "26", "2f", "10", "19", "02", "0b", "d7", "de",
            "c5", "cc", "f3", "fa", "e1", "e8", "9f", "96", "8d", "84", "bb", "b2", "a9", "a0", "47", "4e", "55", "5c",
            "63", "6a", "71", "78", "0f", "06", "1d", "14", "2b", "22", "39", "30", "9a", "93", "88", "81", "be", "b7",
            "ac", "a5", "d2", "db", "c0", "c9", "f6", "ff", "e4", "ed", "0a", "03", "18", "11", "2e", "27", "3c", "35",
            "42", "4b", "50", "59", "66", "6f", "74", "7d", "a1", "a8", "b3", "ba", "85", "8c", "97", "9e", "e9", "e0",
            "fb", "f2", "cd", "c4", "df", "d6", "31", "38", "23", "2a", "15", "1c", "07", "0e", "79", "70", "6b", "62",
            "5d", "54", "4f", "46"
        };

        private static string[] by11 =
        {
            "00", "0b", "16", "1d", "2c", "27", "3a", "31", "58", "53", "4e", "45", "74", "7f", "62", "69", "b0", "bb",
            "a6", "ad", "9c", "97", "8a", "81", "e8", "e3", "fe", "f5", "c4", "cf", "d2", "d9", "7b", "70", "6d", "66",
            "57", "5c", "41", "4a", "23", "28", "35", "3e", "0f", "04", "19", "12", "cb", "c0", "dd", "d6", "e7", "ec",
            "f1", "fa", "93", "98", "85", "8e", "bf", "b4", "a9", "a2", "f6", "fd", "e0", "eb", "da", "d1", "cc", "c7",
            "ae", "a5", "b8", "b3", "82", "89", "94", "9f", "46", "4d", "50", "5b", "6a", "61", "7c", "77", "1e", "15",
            "08", "03", "32", "39", "24", "2f", "8d", "86", "9b", "90", "a1", "aa", "b7", "bc", "d5", "de", "c3", "c8",
            "f9", "f2", "ef", "e4", "3d", "36", "2b", "20", "11", "1a", "07", "0c", "65", "6e", "73", "78", "49", "42",
            "5f", "54", "f7", "fc", "e1", "ea", "db", "d0", "cd", "c6", "af", "a4", "b9", "b2", "83", "88", "95", "9e",
            "47", "4c", "51", "5a", "6b", "60", "7d", "76", "1f", "14", "09", "02", "33", "38", "25", "2e", "8c", "87",
            "9a", "91", "a0", "ab", "b6", "bd", "d4", "df", "c2", "c9", "f8", "f3", "ee", "e5", "3c", "37", "2a", "21",
            "10", "1b", "06", "0d", "64", "6f", "72", "79", "48", "43", "5e", "55", "01", "0a", "17", "1c", "2d", "26",
            "3b", "30", "59", "52", "4f", "44", "75", "7e", "63", "68", "b1", "ba", "a7", "ac", "9d", "96", "8b", "80",
            "e9", "e2", "ff", "f4", "c5", "ce", "d3", "d8", "7a", "71", "6c", "67", "56", "5d", "40", "4b", "22", "29",
            "34", "3f", "0e", "05", "18", "13", "ca", "c1", "dc", "d7", "e6", "ed", "f0", "fb", "92", "99", "84", "8f",
            "be", "b5", "a8", "a3"
        };

        private static string[] by13 =
        {
            "00", "0d", "1a", "17", "34", "39", "2e", "23", "68", "65", "72", "7f", "5c", "51", "46", "4b", "d0", "dd",
            "ca", "c7", "e4", "e9", "fe", "f3", "b8", "b5", "a2", "af", "8c", "81", "96", "9b", "bb", "b6", "a1", "ac",
            "8f", "82", "95", "98", "d3", "de", "c9", "c4", "e7", "ea", "fd", "f0", "6b", "66", "71", "7c", "5f", "52",
            "45", "48", "03", "0e", "19", "14", "37", "3a", "2d", "20", "6d", "60", "77", "7a", "59", "54", "43", "4e",
            "05", "08", "1f", "12", "31", "3c", "2b", "26", "bd", "b0", "a7", "aa", "89", "84", "93", "9e", "d5", "d8",
            "cf", "c2", "e1", "ec", "fb", "f6", "d6", "db", "cc", "c1", "e2", "ef", "f8", "f5", "be", "b3", "a4", "a9",
            "8a", "87", "90", "9d", "06", "0b", "1c", "11", "32", "3f", "28", "25", "6e", "63", "74", "79", "5a", "57",
            "40", "4d", "da", "d7", "c0", "cd", "ee", "e3", "f4", "f9", "b2", "bf", "a8", "a5", "86", "8b", "9c", "91",
            "0a", "07", "10", "1d", "3e", "33", "24", "29", "62", "6f", "78", "75", "56", "5b", "4c", "41", "61", "6c",
            "7b", "76", "55", "58", "4f", "42", "09", "04", "13", "1e", "3d", "30", "27", "2a", "b1", "bc", "ab", "a6",
            "85", "88", "9f", "92", "d9", "d4", "c3", "ce", "ed", "e0", "f7", "fa", "b7", "ba", "ad", "a0", "83", "8e",
            "99", "94", "df", "d2", "c5", "c8", "eb", "e6", "f1", "fc", "67", "6a", "7d", "70", "53", "5e", "49", "44",
            "0f", "02", "15", "18", "3b", "36", "21", "2c", "0c", "01", "16", "1b", "38", "35", "22", "2f", "64", "69",
            "7e", "73", "50", "5d", "4a", "47", "dc", "d1", "c6", "cb", "e8", "e5", "f2", "ff", "b4", "b9", "ae", "a3",
            "80", "8d", "9a", "97"
        };

        private static string[] by14 =
        {
            "00", "0e", "1c", "12", "38", "36", "24", "2a", "70", "7e", "6c", "62", "48", "46", "54", "5a", "e0", "ee",
            "fc", "f2", "d8", "d6", "c4", "ca", "90", "9e", "8c", "82", "a8", "a6", "b4", "ba", "db", "d5", "c7", "c9",
            "e3", "ed", "ff", "f1", "ab", "a5", "b7", "b9", "93", "9d", "8f", "81", "3b", "35", "27", "29", "03", "0d",
            "1f", "11", "4b", "45", "57", "59", "73", "7d", "6f", "61", "ad", "a3", "b1", "bf", "95", "9b", "89", "87",
            "dd", "d3", "c1", "cf", "e5", "eb", "f9", "f7", "4d", "43", "51", "5f", "75", "7b", "69", "67", "3d", "33",
            "21", "2f", "05", "0b", "19", "17", "76", "78", "6a", "64", "4e", "40", "52", "5c", "06", "08", "1a", "14",
            "3e", "30", "22", "2c", "96", "98", "8a", "84", "ae", "a0", "b2", "bc", "e6", "e8", "fa", "f4", "de", "d0",
            "c2", "cc", "41", "4f", "5d", "53", "79", "77", "65", "6b", "31", "3f", "2d", "23", "09", "07", "15", "1b",
            "a1", "af", "bd", "b3", "99", "97", "85", "8b", "d1", "df", "cd", "c3", "e9", "e7", "f5", "fb", "9a", "94",
            "86", "88", "a2", "ac", "be", "b0", "ea", "e4", "f6", "f8", "d2", "dc", "ce", "c0", "7a", "74", "66", "68",
            "42", "4c", "5e", "50", "0a", "04", "16", "18", "32", "3c", "2e", "20", "ec", "e2", "f0", "fe", "d4", "da",
            "c8", "c6", "9c", "92", "80", "8e", "a4", "aa", "b8", "b6", "0c", "02", "10", "1e", "34", "3a", "28", "26",
            "7c", "72", "60", "6e", "44", "4a", "58", "56", "37", "39", "2b", "25", "0f", "01", "13", "1d", "47", "49",
            "5b", "55", "7f", "71", "63", "6d", "d7", "d9", "cb", "c5", "ef", "e1", "f3", "fd", "a7", "a9", "bb", "b5",
            "9f", "91", "83", "8d"
        };

        static string[] RC =
        {
            "01000000", "02000000", "04000000", "08000000", "10000000", "20000000", "40000000", "80000000", "1b000000",
            "36000000"
        };

        public static void Main(string[] args)
        {
            // Console.WriteLine("Enter plaintext 16 letters");
            // string inputText = Console.ReadLine();
            // Console.WriteLine("Enter key 16 letters");
            // string inputKey = Console.ReadLine();
            //
            // string hexText = StringToHex(inputText);
            // string hexKey = StringToHex(inputKey);

            string[,] matrix = FourByFour("0123456789abcdeffedcba9876543210");
            string[,] key = FourByFour("0f1571c947d9e8590cb7add6af7f6798");

            Console.WriteLine("Encryption");
            Encryption(matrix, key);
            Console.WriteLine("Decryption");
            Decryption(matrix, key);
        }

        static void Encryption(string[,] matrix, string[,] key)
        {
            string[,,] newkey = KeyExpansion(key);

            matrix = AddRoundKey(matrix, newkey, 0);

            for (int i = 1; i < 10; i++)
            {
                matrix = Substitution(matrix);
                matrix = ShiftRows(matrix);
                matrix = MixColumns(matrix);
                matrix = AddRoundKey(matrix, newkey, i);
            }

            matrix = Substitution(matrix);
            matrix = ShiftRows(matrix);
            matrix = AddRoundKey(matrix, newkey, 10);

            Print(matrix);
        }

        static void Decryption(string[,] matrix, string[,] key)
        {
            string[,,] newkey = KeyExpansion(key);

            matrix = AddRoundKey(matrix, newkey, 10);

            for (int i = 9; i > 0; i--)
            {
                matrix = InverseShiftRows(matrix);
                matrix = InverseSubstitution(matrix);
                matrix = AddRoundKey(matrix, newkey, i);
                matrix = InverseMixColumns(matrix);
            }

            matrix = InverseShiftRows(matrix);
            matrix = InverseSubstitution(matrix);
            matrix = AddRoundKey(matrix, newkey, 0);

            Print(matrix);
        }

        static string[,] InverseSubstitution(string[,] b)
        {
            string[,] subW = new string[4, 4];
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    int k = HexToDec(b[i, j][0].ToString()),
                        l = HexToDec(b[i, j][1].ToString());
                    subW[i, j] = InverseSbox[k, l];
                }
            }

            return subW;
        }

        static string[,] InverseShiftRows(string[,] matrix)
        {
            string[,] newMatrix = new string[4, 4];
            for (int j = 0; j < 4; j++)
            {
                newMatrix[0, j] = matrix[0, j];
                newMatrix[1, j] = matrix[1, (j + 7) % 4];
                newMatrix[2, j] = matrix[2, (j + 6) % 4];
                newMatrix[3, j] = matrix[3, (j + 5) % 4];
            }

            return newMatrix;
        }

        static string[,] InverseMixColumns(string[,] matrix)
        {
            int[,] field =
            {
                {14, 11, 13, 9},
                {9, 14, 11, 13},
                {13, 9, 14, 11},
                {11, 13, 9, 14}
            };
            string[,] newMatrix = new string[4, 4];
            string[,] resMatrix = new string[4, 4];
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    string f = matrix[j, i][0] + "";
                    string s = matrix[j, i][1] + "";
                    int r = HexToDec(f);
                    int c = HexToDec(s);
                    for (int k = 0; k < 4; k++)
                    {
                        if (field[k, j] == 14)
                            newMatrix[k, j] = HexToBin(by14[r * 16 + c]);
                        else if (field[k, j] == 13)
                            newMatrix[k, j] = HexToBin(by13[r * 16 + c]);
                        else if (field[k, j] == 11)
                            newMatrix[k, j] = HexToBin(by11[r * 16 + c]);
                        else if (field[k, j] == 9)
                            newMatrix[k, j] = HexToBin(by9[r * 16 + c]);
                    }
                }

                resMatrix[0, i] = BinToHex(XoR(XoR(XoR(newMatrix[0, 0], newMatrix[0, 1]), newMatrix[0, 2]),
                    newMatrix[0, 3]));
                resMatrix[1, i] = BinToHex(XoR(XoR(XoR(newMatrix[1, 0], newMatrix[1, 1]), newMatrix[1, 2]),
                    newMatrix[1, 3]));
                resMatrix[2, i] = BinToHex(XoR(XoR(XoR(newMatrix[2, 0], newMatrix[2, 1]), newMatrix[2, 2]),
                    newMatrix[2, 3]));
                resMatrix[3, i] = BinToHex(XoR(XoR(XoR(newMatrix[3, 0], newMatrix[3, 1]), newMatrix[3, 2]),
                    newMatrix[3, 3]));
            }

            return resMatrix;
        }


        // rounds
        static void Print(string[,] matrix)
        {
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    Console.Write(matrix[i, j] + " ");
                }

                Console.WriteLine();
            }

            Console.WriteLine();
        }

        static string[,] Substitution(string[,] b)
        {
            string[,] subW = new string[4, 4];
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    int k = HexToDec(b[i, j][0].ToString()),
                        l = HexToDec(b[i, j][1].ToString());
                    subW[i, j] = Sbox[k, l];
                }
            }

            return subW;
        }

        static string[,] ShiftRows(string[,] matrix)
        {
            string[,] newMatrix = new string[4, 4];
            for (int j = 0; j < 4; j++)
            {
                newMatrix[0, j] = matrix[0, j];
                newMatrix[1, j] = matrix[1, (j + 5) % 4];
                newMatrix[2, j] = matrix[2, (j + 6) % 4];
                newMatrix[3, j] = matrix[3, (j + 7) % 4];
            }

            return newMatrix;
        }

        static string[,] MixColumns(string[,] matrix)
        {
            int[,] field =
            {
                {2, 3, 1, 1},
                {1, 2, 3, 1},
                {1, 1, 2, 3},
                {3, 1, 1, 2}
            };
            string[,] newMatrix = new string[4, 4];
            string[,] resMatrix = new string[4, 4];
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    string f = matrix[j, i][0] + "";
                    string s = matrix[j, i][1] + "";
                    int r = HexToDec(f);
                    int c = HexToDec(s);
                    for (int k = 0; k < 4; k++)
                    {
                        if (field[k, j] == 2)
                            newMatrix[k, j] = HexToBin(by2[r * 16 + c]);
                        else if (field[k, j] == 3)
                            newMatrix[k, j] = HexToBin(by3[r * 16 + c]);
                        else newMatrix[k, j] = HexToBin(matrix[j, i]);
                    }
                }

                resMatrix[0, i] = BinToHex(XoR(XoR(XoR(newMatrix[0, 0], newMatrix[0, 1]), newMatrix[0, 2]),
                    newMatrix[0, 3]));
                resMatrix[1, i] = BinToHex(XoR(XoR(XoR(newMatrix[1, 0], newMatrix[1, 1]), newMatrix[1, 2]),
                    newMatrix[1, 3]));
                resMatrix[2, i] = BinToHex(XoR(XoR(XoR(newMatrix[2, 0], newMatrix[2, 1]), newMatrix[2, 2]),
                    newMatrix[2, 3]));
                resMatrix[3, i] = BinToHex(XoR(XoR(XoR(newMatrix[3, 0], newMatrix[3, 1]), newMatrix[3, 2]),
                    newMatrix[3, 3]));
            }

            return resMatrix;
        }

        // static string By2(string a)
        // {
        //     if (a[0]=='0')
        //     {
        //         return a.Substring(1).PadRight(8,'0');
        //     }
        //
        //     return a.Substring(1, 7).PadRight(8, '1');
        // }
        //
        // static string By3(string a)
        // {
        //     string a1 = a;
        //     string a2 = By2(a);
        //     return XoR(a1,a2);
        // }
        static string[,] AddRoundKey(string[,] matrix, string[,,] key, int i)
        {
            string xor = "";
            for (int j = 0; j < 4; j++)
            {

                for (int l = 0; l < 4; l++)
                {
                    string k = key[i, l, j];
                    string m = matrix[j, l].ToUpper();
                    xor += XoR(HexToBin(m), HexToBin(k));
                }
            }

            string[,] newMatrix = new string[4, 4];
            string str = BinToHex(xor);
            
            for (int j = 0, l = 0; j < 4; j++)
            {
                for (int k = 0; k < 4; k++, l += 2)
                {
                    newMatrix[j, k] = str[l] + "" + str[l + 1];
                }
            }

            return newMatrix;
        }

        // key exp
        static string[,,] KeyExpansion(string[,] fbf)
        {
            string[,,] w = new string[11, 4, 4];
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    w[0, i, j] = fbf[j, i];
                }
            }

            for (int i = 1, j = 0; i < 11; j++, i++)
            {
                string a = w[i - 1, 0, 0] + w[i - 1, 0, 1] + w[i - 1, 0, 2] + w[i - 1, 0, 3];
                string b = w[i - 1, 1, 0] + w[i - 1, 1, 1] + w[i - 1, 1, 2] + w[i - 1, 1, 3];
                string c = w[i - 1, 2, 0] + w[i - 1, 2, 1] + w[i - 1, 2, 2] + w[i - 1, 2, 3];
                string d = w[i - 1, 3, 0] + w[i - 1, 3, 1] + w[i - 1, 3, 2] + w[i - 1, 3, 3];
                string w0 = BinToHex(XoR(FunctionG(w, i - 1, j), HexToBin(a)));
                string w1 = BinToHex(XoR(HexToBin(w0), HexToBin(b)));
                string w2 = BinToHex(XoR(HexToBin(w1), HexToBin(c)));
                string w3 = BinToHex(XoR(HexToBin(w2), HexToBin(d)));
                for (int k = 0, l = 0; l < 4; l++, k += 2)
                {
                    w[i, 0, l] = w0[k] + "" + w0[k + 1];
                    w[i, 1, l] = w1[k] + "" + w1[k + 1];
                    w[i, 2, l] = w2[k] + "" + w2[k + 1];
                    w[i, 3, l] = w3[k] + "" + w3[k + 1];
                }
            }

            return w;
        }

        static string FunctionG(string[,,] w, int o, int j)
        {
            // shift row
            string[] b = new string[4];
            b[0] = w[o, 3, 1];
            b[1] = w[o, 3, 2];
            b[2] = w[o, 3, 3];
            b[3] = w[o, 3, 0];

            string[] subW = new string[4];
            string a = "";
            for (int i = 0; i < 4; i++)
            {
                int k = HexToDec(b[i][0] + ""),
                    l = HexToDec(b[i][1] + "");

                subW[i] = Sbox[k, l];
                a += subW[i];
            }

            string xor = XoR(HexToBin(a.ToUpper()), HexToBin(RC[j]));
            return xor;
        }

        static string HexToBin(string hex)
        {
            string bin = String.Join(String.Empty,
                hex.Select(
                    c => Convert.ToString(Convert.ToInt32(c.ToString(), 16), 2).PadLeft(4, '0')
                ).ToArray()
            );

            return bin;
        }

        static int HexToDec(string hex)
        {
            return Convert.ToInt32(hex, 16);
        }

        static string BinToHex(string binary)
        {
            if (string.IsNullOrEmpty(binary))
                return binary;

            StringBuilder result = new StringBuilder(binary.Length / 8 + 1);

            int mod4Len = binary.Length % 8;
            if (mod4Len != 0)
            {
                binary = binary.PadLeft(((binary.Length / 8) + 1) * 8, '0');
            }

            for (int i = 0; i < binary.Length; i += 8)
            {
                string eightBits = binary.Substring(i, 8);
                result.AppendFormat("{0:X2}", Convert.ToByte(eightBits, 2));
            }

            return result.ToString();
        }

        static string XoR(string bfirst, string bsecond)
        {
            string xor = "";
            for (int i = 0; i < bfirst.Length; i++)
            {
                if (bfirst[i] == bsecond[i])
                {
                    xor += "0";
                }
                else
                {
                    xor += "1";
                }
            }

            return xor;
        }

        // convert text to hex
        static string StringToHex(string plaintext)
        {
            byte[] asciiBytes = Encoding.ASCII.GetBytes(plaintext);
            var hexString = BitConverter.ToString(asciiBytes);
            return hexString.Replace("-", "");
        }

        static string[,] FourByFour(string value)
        {
            string[,] fbf = new string[4, 4];

            for (int i = 0, k = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; k += 2, j++)
                {
                    fbf[j, i] = value[k] + "" + value[k + 1];
                }
            }

            return fbf;
        }

    }
}
