# RSA Encryption & Linear Congruential Generator (LCG) Prototype

![Java](https://img.shields.io/badge/Language-Java%20SE-ED8B00?logo=openjdk&logoColor=white)
![Domain](https://img.shields.io/badge/Domain-Applied%20Cryptography-blue)
![Build](https://img.shields.io/badge/Build-Apache%20Ant%20%2F%20NetBeans-E05D44)
![License](https://img.shields.io/badge/License-MIT-green)

An applied cryptographic prototype implementing asymmetric **RSA Encryption** combined with a **Linear Congruential Generator (LCG)** pseudo-random number generator for private key candidate derivation, modular multiplicative inverse calculation, and Base64 cipher transport.

---

## Cryptographic & Mathematical Foundation

### 1. RSA Key Generation
- **Prime Selection:** Computes modulus $n = p \times q$ and Euler's totient function $\phi(n) = (p - 1)(q - 1)$.
- **Public Key:** Selects public exponent $e$ such that $\gcd(e, \phi(n)) = 1$.
- **Modular Multiplicative Inverse:** Derives private exponent $d$ satisfying:
  $$d \cdot e \equiv 1 \pmod{\phi(n)}$$

### 2. Linear Congruential Generator (LCG)
Generates pseudo-random sequence candidates according to the recurrence relation:
$$X_{n+1} = (a \cdot X_n + c) \pmod m$$
Where:
- $X$: Sequence values
- $m$: Modulus ($\phi(n)$)
- $a$: Multiplier
- $c$: Increment
- $X_0$: Initial random seed

### 3. Cipher Operations & Base64 Encoding
- **Encryption:** $C \equiv M^e \pmod n$
- **Decryption:** $M \equiv C^d \pmod n$
- **Transport Encoding:** Formats raw ciphertext blocks into standard RFC 4648 Base64 strings.

---

## Project Structure

```
rsa-lcg-prototype-archive/
├── src/
│   └── cryptoapp/
│       └── RSAEncryptionGUI.java   # RSA algorithm & LCG private key derivation
├── build.xml                       # Apache Ant build configuration
└── nbproject/                      # NetBeans project configuration
```

---

## Compilation & Execution

```bash
# Compile
javac -d bin src/cryptoapp/RSAEncryptionGUI.java

# Run demonstration
java -cp bin cryptoapp.RSAEncryptionGUI
```

### Sample Output

```text
Private Key (traditional): (3233, 2753)
Private Key (LCG-based): (3233, 2753)
Encrypted Message: 3000 1313 745 745 2185 898
Encrypted Message (Base64): MzAwMCAxMzEzIDc0NSA3NDUgMjE4NSA4OTg=
Decrypted Message (Base64): 3000 1313 745 745 2185 898
Decrypted Message (traditional): Hello!
Decrypted Message (LCG-based): Hello!
```

---

## Author & License

- **Developer:** Hossam Ahmed ([@KabtinBandar](https://github.com/KabtinBandar))
- **License:** MIT
