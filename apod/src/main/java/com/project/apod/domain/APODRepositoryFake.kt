package com.project.apod.domain

import com.project.apod.entities.APODResponse
import com.project.core.domain.BaseRepository
import java.util.*

class APODRepositoryFake : BaseRepository<List<APODResponse>> {
    override suspend fun loadAsync(startDate: String, endDate: String): List<APODResponse> {
        val list = mutableListOf<APODResponse>()

        list.add(
            APODResponse(
                "Antonio Tartarini",
                "2022-04-01",
                "The natural filter of a hazy atmosphere offered this recognizable architecture and sunset telephoto view on March 27. Dark against the solar disk, large sunspots in solar active regions 2975 and 2976 are wedged between the Duomo of Pisa and its famous Leaning Tower. Only one day later, Sun-staring spacecraft watched active region 2975 unleash a frenzy of solar flares along with two coronal mass ejections. The largest impacted the magnetosphere on March 31 triggering a geomagnetic storm and aurorae in high-latitude night skies. On March 30, active region 2975 erupted again with a powerful X-class solar flare that caused a temporary radio blackout on planet Earth.",
                "https://apod.nasa.gov/apod/image/2204/sunspotsleaningtowerofpisa.jpg",
                "image",
                "v1",
                "Leaning Tower, Active Sun",
                "https://apod.nasa.gov/apod/image/2204/sunspotsleaningtowerofpisa1024.jpg"
            )
        )

        list.add(
            APODResponse(
                "Jason Dain",
                "2022-04-02",
                "This almost otherworldly display of northern lights was captured in clear skies during the early hours of March 31 from 44 degrees north latitude, planet Earth. In a five second exposure the scene looks north from Martinique Beach Provincial Park in Nova Scotia, Canada. Stars of the W-shaped constellation Cassiopeia shine well above the horizon, through the red tint of the higher altitude auroral glow. Auroral activity was anticipated by skywatchers alerted to the possibility of stormy space weather by Sun-staring spacecraft. The predicted geomagnetic storm was sparked as a coronal mass ejection, launched from prolific solar active region 2975, impacted our fair planet's magnetosphere.",
                "https://apod.nasa.gov/apod/image/2204/Z62_5747-Edit.jpg",
                "image",
                "v1",
                "Nova Scotia Northern Lights",
                "https://apod.nasa.gov/apod/image/2204/Z62_5747-Edit1090.jpg"
            )
        )

        list.add(
            APODResponse(
                null,
                "2022-04-03",
                "Our Earth is not at rest.  The Earth moves around the Sun.  The Sun orbits the center of the Milky Way Galaxy.  The Milky Way Galaxy orbits in the Local Group of Galaxies.  The Local Group falls toward the Virgo Cluster of Galaxies.  But these speeds are less than the speed that all of these objects together move relative to the cosmic microwave background radiation (CMBR).  In the featured all-sky map from the COBE satellite in 1993, microwave light in the Earth's direction of motion appears blueshifted and hence hotter, while microwave light on the opposite side of the sky is redshifted and colder.  The map indicates that the Local Group moves at about 600 kilometers per second relative to this primordial radiation.  This high speed was initially unexpected and its magnitude is still unexplained.  Why are we moving so fast?  What is out there?",
                "https://apod.nasa.gov/apod/image/2204/CmbDipole_cobe_960.jpg",
                "image",
                "v1",
                "CMB Dipole: Speeding Through the Universe",
                "https://apod.nasa.gov/apod/image/2204/CmbDipole_cobe_960.jpg"
            )
        )

        list.add(
            APODResponse(
                "Christophe Suarez",
                "2022-04-04",
                "No, the car was not in danger of being vacuumed into space by the big sky vortex. For one reason, the vortex was really an aurora, and since auroras are created by particles striking the Earth from space, they do not create a vacuum. This rapidly developing auroral display was caused by a Coronal Mass Ejection from the Sun that passed by the Earth closely enough to cause a ripple in Earth's magnetosphere.  The upper red parts of the aurora occur over 250 kilometers high with its red glow created by atmospheric atomic oxygen directly energized by incoming particles. The lower green parts of the aurora occur over 100 kilometers high with its green glow created by atmospheric atomic oxygen energized indirectly by collisions with first-energized molecular nitrogen.  Below 100 kilometers, there is little atomic oxygen, which is why auroras end abruptly. The concentric cylinders depict a dramatic auroral corona as seen from the side. The featured image was created from a single 3-second exposure taken in mid-March over Lake Myvatn in Iceland.    April is: Global Astronomy Month",
                "https://apod.nasa.gov/apod/image/2204/VortexAurora_Suarez_1920.jpg",
                "image",
                "v1",
                "A Vortex Aurora over Iceland",
                "https://apod.nasa.gov/apod/image/2204/VortexAurora_Suarez_1080.jpg"
            )
        )

        list.add(
            APODResponse(
                "Neven Krcmarek",
                "2022-04-05",
                "On the upper right, dressed in blue, is the Pleiades.  Also known as the Seven Sisters and M45, the Pleiades is one of the brightest and most easily visible open clusters on the sky. The Pleiades contains over 3,000 stars, is about 400 light years away, and only 13 light years across. Surrounding the stars is a spectacular blue reflection nebula made of fine dust.  A common legend is that one of the brighter stars faded since the cluster was named. On the lower left, shining in red, is the California Nebula.  Named for its shape, the California Nebula is much dimmer and hence harder to see than the Pleiades.  Also known as NGC 1499, this mass of red glowing hydrogen gas is about 1,500 light years away. Although about 25 full moons could fit between them, the featured wide angle, deep field image composite has captured them both.  A careful inspection of the deep image will also reveal the star forming region IC 348 and the molecular cloud LBN 777 (the Baby Eagle Nebula).",
                "https://apod.nasa.gov/apod/image/2204/Calif2Pleiades_Krcmarek_10000.jpg",
                "image",
                "v1",
                "Seven Sisters versus California",
                "https://apod.nasa.gov/apod/image/2204/Calif2Pleiades_Krcmarek_1080.jpg"
            )
        )

        return Collections.unmodifiableList(list)
    }
}