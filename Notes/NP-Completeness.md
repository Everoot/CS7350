# NP-Completeness

NP-completeness and the classes P and NP



**The class P** consists of those problems that are solvable in polynomial time. More specifically, they are problems that can be solved in $O(n^k)$ time for some constant k, where n is the size of the input to the problem. Most of the problems examined in previous chapters belong to P.



**The class NP** consists of those problems that are "verifiable" in polynomial time. What do we mean by a problem being verifiable? If you were somehow given a "certificate" of a problem being verifiable? If you were somehow given a "certificate" of a solution, then you could verify that the certificate is correct in time polynomial in the size of the input to the problem. For example, in the hamiltonian-cycle problem, given a directed graph $G=(V,E)$, a certificate would be a sequence $(v_1, v_2, v_3, ..., v_{|v|})$ of $|V|$ vertices. You could check in polynomial time that the sequence contains each of the $|V|$ vertices exactly once, that $v_i, v_{i+1}\in E \text{ for } i = 1, 2, 3, ..., |V|-1$, and that($|v_{|V|},v_1| \in E$).



Any problem in P also beings to NP, since if a problem belongs to P then it is solvable in polynomial time without even being supplied a certificate. We'll formalize this notion later in this chapter, but for now you can believe that $P \subseteq NP$. The famous open question is whether P is proper subset of NP.





Informally, a problem belongs to the class NPC--and we call it NP-complete--if it belongs to NP and is as "hard" as any problem in NP. We'll formally define what it means to be as hard as any problem in NP later in this chapter.  In the meantime, we state without proof that if any NP-complete problem can be solved in polynomial time, then every problem in NP has a polynomial-time algorithm. Most theoretical computer scientists believe that the NP-complete problems are intractable, since given the wide range of NP-complete problems that have been studied to date -- without anyone having discovered a polynomial-time solution to any of them -- it would be truly astounding if all of them could be solved in polynomial time. Yet, given the effort devoted thus far to proving that Np-complete problems are intractable--without a conclusive outcome--we cannot rule out the possibility that the NP-complete problems could turn out to be solvable in polynomial time.