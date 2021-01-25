import {
  Component,
  OnInit,
  Input,
  OnChanges,
  SimpleChanges,
} from '@angular/core';
import { CulturalOfferLocation } from '../../../models/culutral-offer-location';
import { environment } from '../../../../environments/environment.prod';
import * as Mapboxgl from 'mapbox-gl';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Geocoder } from '../../../models/geocoder';
import { DynamicComponentService } from 'src/app/services/dynamic-component.service';
import { MapItemOverviewComponent } from '../map-item-overview/map-item-overview.component';
import { CulturalOfferResponse } from 'src/app/models/cultural-offer-response';

@Component({
  selector: 'app-map-item',
  templateUrl: './map-item.component.html',
  styleUrls: ['./map-item.component.scss'],
})
export class MapItemComponent implements OnInit, OnChanges {
  @Input()
  culturalOffers: CulturalOfferResponse[];
  @Input()
  location: string;

  mapa: Mapboxgl.Map;
  markers: Mapboxgl.Marker[] = [];
  mapInitialized: boolean = false;

  constructor(
    private httpClient: HttpClient,
    private dynamicComponentService: DynamicComponentService
  ) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (this.mapInitialized) {
      if (changes['culturalOffers']) {
        this.removeMarkers();
        this.updateMarkers();
      }
      if (changes['location']) this.focusOnLocation();
    }
  }

  ngOnInit(): void {
    this.initializeMap();
    this.updateMarkers();
  }
  initializeMap(): void {
    (Mapboxgl as any).accessToken = environment.mapboxKey;

    this.mapa = new Mapboxgl.Map({
      container: 'map-mapbox',
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [20.426773, 44.9979649], //LNG, LAT
      zoom: 5.5,
    });

    this.mapInitialized = true;
  }

  createMarkers(): void {
    this.culturalOffers.forEach((offer) => {
      let popupContent = this.dynamicComponentService.injectComponent(
        MapItemOverviewComponent,
        (x) => (x.offer = offer)
      );
      const marker = new Mapboxgl.Marker({
        draggable: false,
      })
        .setLngLat([offer.location.longitude, offer.location.latitude])
        .setPopup(
          new Mapboxgl.Popup({ maxWidth: '1000' }).setDOMContent(popupContent)
        );

      this.markers.push(marker);
    });
  }

  updateMarkers(): void {
    this.createMarkers();
    //add markers on the map
    this.markers.forEach((marker) => marker.addTo(this.mapa));
  }

  removeMarkers(): void {
    this.markers.forEach((marker) => marker.remove());
    this.markers = [];
  }
  focusOnLocation() {
    this.location = this.capitalize(this.location);
    let api_url = `https://api.mapbox.com/geocoding/v5/mapbox.places/
                    ${this.location}.json?access_token=${environment.mapboxKey}`;

    let response = this.httpClient.get<Geocoder>(api_url);

    response.subscribe((geo: Geocoder) => {
      if (geo.features[0]) {
        this.mapa.setCenter(
          geo.features[0].center as [number, number]
        );
        this.mapa.setZoom(13.5);
      } //mozda error staviti
    });
  }

  capitalize = (s: string) => {
    return s.charAt(0).toUpperCase() + s.slice(1);
  };
}
