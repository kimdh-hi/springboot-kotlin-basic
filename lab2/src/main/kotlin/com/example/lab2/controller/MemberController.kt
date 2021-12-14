package com.example.lab2.controller

import com.example.lab2.dto.request.MemberDto
import com.example.lab2.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/members")
@RestController
class MemberController(private val memberService: MemberService) {

    @GetMapping("/{memberId}")
    fun getMember(@PathVariable memberId: Long) = ResponseEntity.ok().body(memberService.getMember(memberId))

    @PostMapping("/signup")
    fun postMember(@RequestBody memberDto: MemberDto) = ResponseEntity.status(201).body(memberService.saveMember(memberDto))

    @PutMapping("/{memberId}")
    fun putMember(
        @PathVariable memberId: Long,
        @RequestBody memberDto: MemberDto) = ResponseEntity.ok().body(memberService.updateMember(memberId, memberDto))

    @DeleteMapping("/{memberId}")
    fun deleteMember(@PathVariable memberId: Long) = ResponseEntity.ok().body(memberService.deleteMember(memberId))
}