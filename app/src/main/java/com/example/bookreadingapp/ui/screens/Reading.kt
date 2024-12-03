package com.example.bookreadingapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.ui.BookReadingApp
import com.example.bookreadingapp.ui.extensions.detectedTapWithoutSwipe
import com.example.bookreadingapp.ui.theme.BookReadingAppTheme
import com.example.bookreadingapp.ui.utils.BookCover


/**
 * Reading screen containing the book to read
 */
@Composable
fun Reading(
    book: Book?,
    readingMode: Boolean,
    toggleReadingMode: () -> Unit,
    currentChapterId: Int?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                // tap gesture for toggling reading mode
                detectTapGestures(
                    onTap = { toggleReadingMode() }
                )
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .testTag("reading_screen")
        ) {
            Text(
                text = stringResource(R.string.reading),
                style = MaterialTheme.typography.displayLarge
            )
            PageScrollLazyColumn(
                book = book
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        // Display the chapter navigation only if not in reading mode
        if (!readingMode) {
            ChapterNavigation(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

/**
 * Book display on the reading screen
 */
@Composable
fun BookDisplay(
    @DrawableRes imageResourceId: Int,
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ) {
        BookCover(imageResourceId)
        Text(
            text = stringResource(titleResourceId),
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
        )
    }
}

/**
 * The button used to navigate through a chapter
 */
@Composable
fun ChapterNavigation(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    ){
        Button(
            onClick = {/** TO DO: Go back to previous chapter (page?) */},
        ){
            Text(
                text = stringResource(R.string.prev_chap),
                style = MaterialTheme.typography.labelSmall
            )
        }

        Button(
            onClick = {/** TO DO: Go back to next chapter (page?) */},
        ){
            Text(
                text = stringResource(R.string.next_chap),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
/**
 * Column where book text will be placed
 */
@Composable
fun PageScrollLazyColumn(
    book: Book?
) {
    //filler text for now
    val textPages = listOf(
        "The quick brown fox jumps over a lazy moon, its lithe body weaving through the cool, silvery mist that blankets the meadow. The moon, pale and full, casts an eerie glow on the landscape, making everything seem just a little more surreal. In the distance, a herd of deer grazes quietly, their delicate movements graceful against the stillness of the night. A soft breeze rustles through the tall grass, carrying with it the scent of pine and earth, as if the forest itself is breathing alongside the creatures of the land.",
        "In the bustling marketplace, an old merchant with a weathered face and hands stained with years of labor carefully arranges a collection of emerald-studded rings on a velvet cloth. His eyes gleam with pride as he beckons customers to admire his wares. Around him, the marketplace is alive with the sounds of haggling, laughter, and the rhythmic clink of coins changing hands. Colorful fabric stalls flutter in the breeze, and the aroma of freshly baked bread mixes with the rich spices of exotic perfumes carried by the wind from distant lands.",
        "Nearby, a creaky windmill turns slowly in the wind, its weathered wooden blades spinning with an ancient rhythm. The miller, an elderly man with a crooked back and a tired smile, watches it turn, lost in thought. The grain inside the mill is being ground into flour, its scent mingling with the earthiness of the surrounding fields. Beyond the windmill, vast stretches of golden wheat sway gently in the breeze, their stalks like waves in a sea of amber, endlessly rolling toward the horizon.",
        "As the wind whispers through the trees, the air feels cool and fresh, carrying with it the promise of a coming rainstorm. The leaves rustle like soft whispers, as if the forest itself is telling stories to anyone who cares to listen. A small stream trickles over rocks, its water clear and shimmering in the fading light. Birds sing their last songs of the day from the treetops, their voices a final chorus before nightfall descends. In the distance, a lone owl hoots, its call echoing through the stillness of the woods.",
        "A distant mountain range looms on the horizon, bathed in the warm, golden glow of sunset. The peaks, dusted with snow, catch the last rays of sunlight, glowing like the tips of ancient swords. The air is thick with the scent of pine and the faint smell of smoke from a campfire somewhere in the valley below. As the sun dips lower, the sky is painted with streaks of orange, pink, and purple, and the shadows of the mountains stretch long across the land. A hush falls over the world, as if nature itself is holding its breath in awe of the beauty unfolding above.",
        "In the village, the baker is preparing a fresh batch of bread, his hands deftly shaping the dough into perfect loaves. The smell of the warm bread fills the air, drawing customers from every corner of the village. The bakery’s windows are fogged up with the warmth inside, and the glow from the hearth casts a soft, golden light on the faces of those who wait patiently for their turn. The baker's apron is dusted with flour, and his face bears a gentle smile as he pulls the golden-brown loaves from the oven, their crusts crackling with the promise of warmth and comfort.",
        "On the other side of the river, the fisherman casts his net into the still waters, watching intently as it spreads across the surface like a silken web. The river, dark and mysterious, mirrors the twilight sky above, and the gentle flow of the water lulls the world into a state of peaceful calm. Birds take flight, silhouetted against the fading light, and the sound of crickets begins to rise, filling the evening air with their rhythmic chirping. The fisherman’s boat drifts lazily with the current, his eyes scanning the water for the telltale signs of a catch, his movements slow and practiced.",
        "Children are playing near the town square, their laughter echoing through the narrow streets as they chase one another under the watchful eyes of the town’s stone buildings. The square is alive with activity—vendors hawking their goods, families strolling past, and the clinking of the town bell that chimes every hour. The air is filled with the sweet scent of fresh fruit, and the cobblestone streets are worn smooth from years of use. Above, the sky is a brilliant blue, unmarred by a single cloud, and the sun casts a warm glow on everything, as if the whole world is basking in the happiness of the moment.",
        "An old man sits on the porch of a weathered farmhouse, his hands resting on his cane as he watches the world go by. His face is lined with the wrinkles of a life well-lived, and his eyes, though clouded with age, still sparkle with the wisdom of years. The sounds of the village reach his ears, faint but familiar—the creak of wooden carts on the dirt road, the calls of birds in the distance, and the hum of conversation in the town square. The porch is quiet, a peaceful haven from the bustle of the world, and the old man is content to simply sit and watch the sun dip lower behind the distant hills.",
        "A cat darts across the road, its fur sleek and black, blending with the shadows as it moves with the agility of a creature born for the night. Its tail flicks in the breeze, a quick, fluid motion that seems to draw the eye as it disappears into the underbrush. The streets are quiet now, save for the occasional sound of footsteps echoing down the narrow alleys. The evening air is cool and crisp, carrying with it the scent of rain that lingers on the horizon. The cat, ever elusive, vanishes into the darkness, leaving only the soft rustle of leaves in its wake.",
        "In the forest, the tall trees sway gently, their leaves shimmering under the soft rays of the afternoon sun. The dappled light filters through the canopy, casting patches of golden warmth on the forest floor. Ferns and mosses cover the ground like a living carpet, soft underfoot and rich with life. A deer steps cautiously through the underbrush, its ears flicking at every sound, its eyes wide with alertness. The forest is alive with the hum of insects, the chirping of birds, and the rustling of small creatures moving through the undergrowth.",
        "At the local pub, the sound of clinking glasses fills the air as patrons enjoy their evening drinks, their voices rising in a friendly hum of conversation. The warmth of the fire crackles in the hearth, casting flickering shadows on the walls. The scent of rich ale and roasted meats mingles with the musty smell of old wood and tobacco smoke. Outside, the wind picks up, rattling the windows, but inside, the atmosphere is warm and inviting, a place where stories are told, friendships are forged, and the troubles of the world are momentarily forgotten.",
        "A woman walks down the cobbled street, her footsteps echoing softly in the quiet evening air. Her dark cloak sways gently with each step, and the faint scent of lavender follows her as she passes by the open windows of the houses. The town is winding down for the night, the last of the shoppers returning home, and the streetlamps flicker to life, casting pools of golden light on the stone path ahead. The woman walks with purpose, her head held high, a figure of grace and mystery in the fading light of dusk.",
        "The moonlight reflects off the calm surface of the lake, creating a silver path that stretches from one shore to the other. The water is still, save for the occasional ripple caused by a fish breaking the surface or a gentle breeze stirring the air. The night is quiet, the only sounds the distant calls of night birds and the rustling of leaves in the trees. On the shore, a lone figure stands, staring out across the water, their silhouette framed by the glow of the moon. The world is peaceful, serene, and utterly still.",
        "In the distance, the sound of a horn can be heard, signaling the arrival of a caravan. The deep, resonant notes echo through the valley, announcing the approach of travelers from afar. The air is thick with anticipation as the caravan winds its way toward the village, its colorful tents and banners fluttering in the wind. The scent of exotic spices and incense drifts on the breeze, a tantalizing promise of the wonders that await. The villagers gather in the square, eager to see what treasures the caravan brings, and the evening is filled with the sounds of drums, laughter, and the clinking of coins as the travelers prepare to unload their goods.",
        "A small bird flutters in and out of the bushes, chirping merrily as it searches for food. Its feathers are a vibrant mix of red and blue, and it hops from branch to branch with the energy of youth. The surrounding woods are quiet, but the bird’s cheerful song fills the air, adding a note of joy to the stillness. It pauses for a moment, cocking its head to the side as if listening for something, before darting off again, its wings a flash of color against the green backdrop of the forest. Every so often, its song is joined by the trill of another bird, creating a symphony of nature that seems to echo through the trees.",
        "The wind picks up, rustling the leaves and sending a few loose branches tumbling to the ground. The air is cool now, carrying the scent of rain that is just beginning to form on the horizon. The trees sway with the gusts, their branches creaking and groaning in the growing strength of the storm. Birds take flight, seeking shelter in the dense canopy, and the forest seems to hold its breath, waiting for the first drop of rain to fall. In the distance, thunder rumbles, a low, rolling sound that promises the arrival of a storm.",
        "The old clock tower in the town square strikes midnight, its chimes echoing through the empty streets. The town is quiet now, its inhabitants asleep in their beds, but the clock continues its steady rhythm, marking the passage of time. The light from the lanterns flickers in the wind, casting long shadows on the cobblestones. The square is empty, save for the occasional stray cat or mouse darting between the buildings. For a moment, the world seems to stand still, caught in the silent embrace of midnight.",
        "A solitary figure walks along the beach, their footprints leaving a temporary mark in the sand before being erased by the lapping waves. The sound of the ocean is rhythmic, almost hypnotic, and the salty air carries with it the scent of the sea. The sky is dark now, dotted with stars, and the moon casts a silvery path across the water. The figure walks slowly, lost in thought, the only company the crashing of the waves and the occasional distant cry of a seabird. There is a sense of solitude, but it is peaceful, as if the beach itself is a sanctuary for those seeking quiet reflection.",
        "A group of travelers gathers around a campfire, their faces illuminated by the flickering flames. The fire crackles and pops, sending sparks into the night air as the group shares stories of their adventures. The night is cool, but the warmth of the fire is comforting, and the smell of roasting meat mingles with the earthy scent of the forest around them. Above, the stars twinkle like distant jewels, casting their ancient light down on the world below. The travelers sit in a loose circle, their voices rising and falling with the rhythm of the fire, the bonds of friendship forged in the flickering glow.",
        "The scent of blooming flowers fills the air as the garden comes to life in the springtime. Flowers of every color and shape bloom in a riot of color—violets, daisies, and roses, their petals soft and fragrant. The air is warm and full of life, with bees buzzing from flower to flower, gathering nectar, and butterflies fluttering lazily through the air. The garden is a place of peace, a refuge from the world outside, where time seems to slow and the beauty of nature takes center stage."
    )


    var currentPage by rememberSaveable { mutableStateOf(0) }

    val swipeThreshold = 300f
    var swipeDetected by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableStateOf(0f) }

    var itemsPerPage by remember { mutableStateOf(1) }

    val boxModifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned { coordinates ->
            val height = coordinates.size.height.toFloat()
            itemsPerPage = (height / 260).toInt()
        }

    val chunkedPages = textPages.chunked(itemsPerPage)

    val swipeModifier = Modifier.pointerInput(Unit) {
        detectHorizontalDragGestures { change, dragAmount ->
            dragOffset += dragAmount * 0.5f

            if (dragOffset > swipeThreshold && !swipeDetected) {
                if (currentPage > 0) {
                    currentPage--
                    swipeDetected = true
                }
            } else if (dragOffset < -swipeThreshold && !swipeDetected) {
                if (currentPage < chunkedPages.lastIndex) {
                    currentPage++
                    swipeDetected = true
                }
            }
            if (swipeDetected) {
                dragOffset = 0f
            }
        }
    }

    LaunchedEffect(currentPage) {
        swipeDetected = false
    }

    Box(modifier = boxModifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                //.border(BorderStroke(4.dp, Color.Black), RectangleShape)
                .padding(16.dp)
                .align(Alignment.Center)
                .then(swipeModifier)
        ) {
            if (currentPage == 0) {
                item {
                    book?.let {
                        BookDisplay(
                            imageResourceId = it.imageResourceId,
                            titleResourceId = it.title
                        )
                    }
                }
            }
            else{
                items(chunkedPages[currentPage].size) { index ->
                    Text(
                        text = chunkedPages[currentPage][index],
                        style = TextStyle(fontSize = 18.sp),
                        modifier = Modifier.padding(bottom = 8.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
        // Display the page number at the bottom of the screen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            if(currentPage != 0) {
                Text(
                    text = "Page ${currentPage} of ${chunkedPages.size - 1}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

