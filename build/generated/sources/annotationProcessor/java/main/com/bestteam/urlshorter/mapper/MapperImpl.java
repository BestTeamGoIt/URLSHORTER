package com.bestteam.urlshorter.mapper;

import com.bestteam.urlshorter.dto.LinkDto;
import com.bestteam.urlshorter.models.Link;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-15T02:17:17+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class MapperImpl implements Mapper {

    @Override
    public Link toEntity(LinkDto linkDto) {
        if ( linkDto == null ) {
            return null;
        }

        Link.LinkBuilder link = Link.builder();

        link.shortLink( linkDto.getShortLink() );
        link.link( linkDto.getLink() );
        link.openCount( linkDto.getOpenCount() );
        link.creationDateTime( linkDto.getCreationDateTime() );
        link.expirationDateTime( linkDto.getExpirationDateTime() );
        link.isActive( linkDto.getIsActive() );

        return link.build();
    }

    @Override
    public LinkDto toDto(Link link) {
        if ( link == null ) {
            return null;
        }

        LinkDto linkDto = new LinkDto();

        linkDto.setShortLink( link.getShortLink() );
        linkDto.setLink( link.getLink() );
        linkDto.setOpenCount( link.getOpenCount() );
        linkDto.setCreationDateTime( link.getCreationDateTime() );
        linkDto.setExpirationDateTime( link.getExpirationDateTime() );
        linkDto.setIsActive( link.getIsActive() );

        return linkDto;
    }

    @Override
    public void merge(LinkDto linkDto, Link link) {
        if ( linkDto == null ) {
            return;
        }

        link.setShortLink( linkDto.getShortLink() );
        link.setLink( linkDto.getLink() );
        link.setOpenCount( linkDto.getOpenCount() );
        link.setCreationDateTime( linkDto.getCreationDateTime() );
        link.setExpirationDateTime( linkDto.getExpirationDateTime() );
        link.setIsActive( linkDto.getIsActive() );
    }
}
