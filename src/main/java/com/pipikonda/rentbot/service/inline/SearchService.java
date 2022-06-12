package com.pipikonda.rentbot.service.inline;

import com.pipikonda.rentbot.bot.model.request.impl.inline_result.InlineQueryResult;
import com.pipikonda.rentbot.bot.model.update.InlineQuery;

import java.util.List;

public interface SearchService {

    List<InlineQueryResult> search(InlineQuery inlineQuery);

    SearchType supports();
}
