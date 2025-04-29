


						<table width="100%" height="35" border="0" cellpadding="0" cellspacing="0" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/05_cen_titlebg.gif">
							<tr>
								<td align="left" valign="middle" style="padding:0 0 0 8"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/05_centitle01.gif" width="134" height="19"></td>
								<td width="50%" align="right" valign="middle" style="padding:0 8 0 0">&nbsp;</td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="center" valign="top" style="padding:20 0 0 0">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="199" align="right">
												<table width="199" height="199" border="0" cellpadding="0" cellspacing="0" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/05_cen31.gif">
													<tr>
														<td align="center" valign="middle"><img src="<%=mainImg%>" width="168" height="168" border="0"></td>
													</tr>
												</table>
											</td>
											<td width="500" style="padding:0 0 0 15">
												<table width="100%" height="200" border="0" cellpadding="0" cellspacing="0" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/05_cen32.gif">
													<tr>
														<td height="40" style="padding:0 0 0 15" class="comm_title"><%=commName%></td>
													</tr>
													<tr>
														<td align="left" valign="top" style="padding:15 20 10 20"><%=StringUtil.getHtmlContents(commSynopsis)%></td>
													</tr>
													<tr>
														<td height="60" align="center" valign="top">
															<table cellpadding="0" cellspacing="0" border="0" width="93%">
																<tr>
																	<td class="c_bg_line_01"></td>
																	<td class="c_bg_line_01"></td>
																	<td class="c_bg_line_01"></td>
																	<td class="c_bg_line_01"></td>
																</tr>
																<tr align="left" valign="middle">
																	<td width="15%" align="center" class="c_table_g_L_03"><strong>시삽</strong></td>
																	<td width="35%" class="c_table_g_RL_04"><span class="c_number"><%=nickName%></span></td>
																	<td width="15%" align="center" class="c_table_g_L_03"><strong>등급</strong></td>
																	<td width="35%" class="c_table_g_RL_04"><span class="c_number"><%=levelName%></span></td>
																</tr>
																<tr align="left" valign="middle">
																	<td width="15%" align="center" class="c_table_g_L_03"><strong>분류</strong></td>
																	<td width="35%" class="c_table_g_RL_04"><span class="c_number"><%=cateName%></span></td>
																	<td width="15%" align="center" class="c_table_g_L_03"><strong>개설일</strong></td>
																	<td width="35%" class="c_table_g_RL_04"><span class="c_number"><%=makeDate%></span></td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="20" colspan="2">&nbsp;</td>
										</tr>
										<tr>
											<td height="20" colspan="2">
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td height="22" align="left" valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/05_cen36.gif" width="90" height="19"></td>
													</tr>

													<tr>
														<td>
															<table width="100%" border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td height="2" class="b_td01"></td>
																</tr>
																<tr>
																	<td height="30" class="b_td02">
																		<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
																			<tr align="center" valign="middle">
																				<td width="54%" class="b_td02" align="center">제목</td>
																				<td width="1%"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21"></td>
																				<td width="14%" class="b_td02" align="center">등록자</td>
																				<td width="1%"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21"></td>
																				<td width="14%" class="b_td02" align="center">등록일</td>
																				<td width="1%"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21"></td>
																				<td width="15%" class="b_td02" align="center">조회수</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr>
																	<td height="1" class="b_td03"> </td>
																</tr>
																<tr>
																	<td>
																		<!-- 리스트 -->
																		<table width="100%" border="0" cellspacing="0" cellpadding="0">
<%	String bbsId = "";
    String bbsName="";
    int seqNo = 0;
    String subject;
    String regName;
    String regDate;
    int hitNo;
    int cnt=0;
    RowSet listup= (RowSet)model.get("rs3");

    while(listup.next()){
        cnt++;

        bbsId = StringUtil.nvl(listup.getString("bbs_id"));
        bbsName = "[" + StringUtil.nvl(listup.getString("bbs_name")) + "] ";
        seqNo = StringUtil.nvl(listup.getString("seq_no"),0);
        subject = StringUtil.nvl(listup.getString("subject"));
        regName = StringUtil.nvl(listup.getString("reg_name"));
        regDate = StringUtil.nvl(listup.getString("reg_date"));
        hitNo   = StringUtil.nvl(listup.getString("hit_no"),0);
%>
																			<tr>
																				<td height="25" onMouseOver="this.className='tdcolor2';" onMouseOut="this.className='tdcolor1'">
																					<table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
																						<tr>
																							<td width="54%" align="left" style="padding-left:9px;">
																								<%=bbsName%><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=commSubBoardShow&pCommId=<%=commId%>&pBbsId=<%=bbsId%>&pSeqNo=<%=seqNo%>&pMode=<%=PMODE %>"><%=subject%> </a>
																							</td>
																							<td width="1%"></td>
																							<td width="14%" align="center"><%=regName%></td>
																							<td width="1%"></td>
																							<td width="14%" align="center"><%=DateTimeUtil.getDateType(1,regDate)%></td>
																							<td width="1%"></td>
																							<td width="15%" align="center"><%=hitNo%></td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																			<tr>
																				<td height="1" class="b_td03" ></td>
																			</tr>
<%	}

    if (cnt<=0) {	%>
																			<tr>
																				<td height="25" align="center">등록된 게시글이 없습니다.</td>
																			</tr>
																			<tr>
																				<td height="1" class="b_td03" ></td>
																			</tr>
<%	}	%>
																		</table>
																		<!-- 리스트 -->
																	</td>
																</tr>
															</table>
														</td>
													</tr>


												</table>
											</td>
										</tr>
									</table>
