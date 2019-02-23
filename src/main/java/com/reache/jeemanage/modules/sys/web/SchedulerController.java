/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 		  __													   *
 *  __   (__`\             000000000     00000000     000000000    *
 * (__`\   \\`\            00      00    00     00    00      00   *
 *  `\\`\   \\ \           00      00    00      00   00      00   *
 *    `\\`\  \\ \          00     00     00       00  00     00    *
 *      `\\`\#\\ \#        00000000      00       00  00000000     *
 *        \_ ##\_ |##      00     00     00       00  00           *
 *        (___)(___)##     00      00    00      00   00           *
 *         (0)  (0)`\##    00      00    00     00    00           *
 *          |~   ~ , \##   000000000     00000000     00           *
 *          |      |  \##                                          *
 *          |     /\   \##         __..---'''''-.._.._             *
 *          |     | \   `\##  _.--'                _  `.           *
 *          Y     |  \    `##'                     \`\  \          *
 *         /      |   \                             | `\ \         *
 *        /_...___|    \                            |   `\\        *
 *       /        `.    |                          /      ##       *
 *      |          |    |                         /      ####      *
 *      |          |    |                        /       ####      *
 *      | () ()    |     \     |          |  _.-'         ##       *
 *      `.        .'      `._. |______..| |-'|                     *
 *        `------'           | | | |    | || |                     *
 *                           | | | |    | || |                     *
 *                           | | | |    | || |                     *
 *                           | | | |    | || |                     *
 *                     _____ | | | |____| || |                     *
 *                    /     `` |-`/     ` |` |                     *
 *                    \________\__\_______\__\                     *
 *                     """""""""   """""""'"""                     *
 *_________________________________________________________________*
 *                                                                 *
 *         We don't produce code, we're just code porters.         *
 *_________________________________________________________________*           
 *******************************************************************
 */
package com.reache.jeemanage.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reache.jeemanage.common.web.BaseController;
import com.reache.jeemanage.modules.sys.service.SchedulerService;

@Controller
@RequestMapping(value = "${adminPath}/sys/scheduler")
public class SchedulerController extends BaseController {
	@Autowired
	private SchedulerService schedulerService;

	@RequestMapping(value = { "list", "" })
	@ResponseBody
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			schedulerService.addJob();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "xxxaa";
	}

}
